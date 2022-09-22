package com.spiffymap.datalog;

import com.spiffymap.datalog.ui.mvp.details.DetailDialog;
import com.spiffymap.datalog.ui.mvp.main.DatalogFrame;
import com.spiffymap.datalog.ui.mvp.stations.StationInputDialog;
import com.spiffymap.datalog.ui.swing.AWTPrinterSettings;
import com.spiffymap.gui.swing.SwingInput;
import com.spiffymap.mvp.client.ClientStorage;
import com.spiffymap.mvp.server.json.JSONSystemServer;
import com.spiffymap.mvp.shared.json.JSONSystem;
import javax.swing.SwingUtilities;
import net.spiffymap.datalog.client.mvp.help.HelpView;
import net.spiffymap.datalog.shared.io.MailSender;
import net.spiffymap.datalog.server.io.ServerMailSender;
import net.spiffymap.datalog.shared.io.ResourceLoader;
import net.spiffymap.datalog.server.datetime.JavaDateAndTimeParserFactory;
import net.spiffymap.datalog.server.formatting.JavaFormatter;
import net.spiffymap.datalog.server.io.FileManagerImpl;
import net.spiffymap.datalog.server.io.JavaClientStorage;
import net.spiffymap.datalog.shared.datetime.DateAndTimeParserFactory;
import net.spiffymap.datalog.shared.ObjectSupplier;
import net.spiffymap.datalog.shared.SpiffyTimer;
import net.spiffymap.datalog.shared.drawing.dxf.DxfFactory;
import net.spiffymap.datalog.shared.formatting.Formatter;
import com.spiffymap.mvp.client.ui.Input;
import net.spiffymap.datalog.shared.mvp.Scheduler;
import com.spiffymap.geo.shared.TextComponentWrapperFactory;
import com.spiffymap.gui.swing.SwingTextComponentWrapperFactory;
import net.spiffymap.datalog.shared.mvp.details.DetailCoordinateView;
import net.spiffymap.datalog.shared.mvp.details.DetailDataView;
import net.spiffymap.datalog.shared.mvp.details.DetailSelectionView;
import net.spiffymap.datalog.shared.mvp.dialogs.DXFSettingsView;
import net.spiffymap.datalog.shared.mvp.drawing.DrawingView;
import net.spiffymap.datalog.shared.mvp.main.DatalogView;
import net.spiffymap.datalog.shared.mvp.settings.SettingsView;
import net.spiffymap.datalog.shared.mvp.stations.StationInputView;
import net.spiffymap.datalog.shared.mvp.stations.StationView;
import net.spiffymap.datalog.shared.mvp.details.DetailView;
import net.spiffymap.datalog.shared.mvp.history.EditHistoryView;
import net.spiffymap.datalog.shared.mvp.information.CodesView;
import net.spiffymap.datalog.shared.mvp.information.InformationView;
import net.spiffymap.datalog.shared.mvp.information.NewsView;
import net.spiffymap.datalog.shared.mvp.logo.LogoView;
import net.spiffymap.datalog.shared.mvp.main.FileManager;
import net.spiffymap.datalog.shared.mvp.problems.DXFCompleteView;
import net.spiffymap.datalog.shared.mvp.problems.ProblemsPresenter;
import net.spiffymap.datalog.shared.mvp.problems.ProblemsView;
import net.spiffymap.datalog.shared.mvp.stations.TraverseDisplayView;
import net.spiffymap.datalog.shared.mvp.stations.TraverseInputView;
import net.spiffymap.datalog.shared.mvp.stations.TraversesView;
import com.spiffymap.mvp.shared.ui.Dialogs;
import net.spiffymap.datalog.shared.drawing.PrinterSettings;

/**
 *
 * @author steve
 * @version 8-Jan-2016
 */
public class SwingObjectSupplier implements ObjectSupplier {

    private final static String STORAGE_FILE = "storage.properties";

    private ClientStorage clientStorage;
    private final Input input = new SwingInput();
    private final DatalogFrame mainFrame;
    private final DrawingView drawingView;
    private final DetailDataView detailDataView;
    private final DetailSelectionView detailSelectionView;
    private final DetailCoordinateView detailCoordinateView;
    private final StationView stationView;
    private final SettingsView settingsView;
    private final TraversesView traversesView;
    private final TraverseInputView traverseInputView;
    private final DXFSettingsView dxfSettingsView;
    private final InformationView informationView;
    private final NewsView newsView;
    private final ProblemsView problemsView;
    private final DXFCompleteView dxfCompleteView;
    private final LogoView logoView;
    private final EditHistoryView editHistoryView;
    private final HelpView helpView;
    private final CodesView codesView;
    private final TraverseDisplayView traverseDisplayView;
    private final Dialogs dialogs;
    private DateAndTimeParserFactory dateAndTimeParserFactory;
    private Scheduler scheduler;
    private ResourceLoader resourceLoader;
    private MailSender mailSender;
    private Formatter formatter;
    private AWTPrinterSettings printerSettings;

    /**
     *
     * @param mainFrame
     * @param drawingView
     * @param detailDataView
     * @param detailSelectionView
     * @param detailCoordinateView
     * @param stationView
     * @param traversesView
     * @param traverseInputView
     * @param settingsView
     * @param dxfSettingsView
     * @param informationView
     * @param newsView
     * @param problemsView
     * @param logoView
     * @param dxfCompleteView
     * @param editHistoryView
     * @param dialogs
     * @param codesView
     * @param traverseDisplayView
     * @param helpView
     */
    public SwingObjectSupplier(DatalogFrame mainFrame, DrawingView drawingView, DetailDataView detailDataView,
            DetailSelectionView detailSelectionView, DetailCoordinateView detailCoordinateView, StationView stationView,
            TraversesView traversesView, TraverseInputView traverseInputView, SettingsView settingsView,
            DXFSettingsView dxfSettingsView, InformationView informationView, NewsView newsView,
            ProblemsView problemsView, LogoView logoView, DXFCompleteView dxfCompleteView,
            EditHistoryView editHistoryView, Dialogs dialogs, CodesView codesView, TraverseDisplayView traverseDisplayView,
            HelpView helpView) {
        this.mainFrame = mainFrame;
        this.drawingView = drawingView;
        drawingView.setObjectSupplier(SwingObjectSupplier.this);
        this.detailDataView = detailDataView;
        this.detailSelectionView = detailSelectionView;
        this.detailCoordinateView = detailCoordinateView;
        this.stationView = stationView;
        this.traversesView = traversesView;
        this.traverseInputView = traverseInputView;
        this.settingsView = settingsView;
        this.dxfSettingsView = dxfSettingsView;
        this.informationView = informationView;
        this.newsView = newsView;
        this.problemsView = problemsView;
        this.logoView = logoView;
        this.dxfCompleteView = dxfCompleteView;
        this.editHistoryView = editHistoryView;
        this.dialogs = dialogs;
        this.codesView = codesView;
        this.traverseDisplayView = traverseDisplayView;
        this.helpView = helpView;
    }

    @Override
    public DatalogView getDatalogView() {
        return mainFrame;
    }

    @Override
    public DrawingView getDrawingView() {
        return drawingView;
    }

    @Override
    public DetailDataView getDetailDataView() {
        return detailDataView;
    }

    @Override
    public InformationView getInformationView() {
        return informationView;
    }

    @Override
    public DetailCoordinateView getDetailCoordinateView() {
        return detailCoordinateView;
    }

    @Override
    public StationView getStationView() {
        return stationView;
    }

    @Override
    public TraversesView getTraversesView() {
        return traversesView;
    }

    @Override
    public SettingsView getSettingsView() {
        return settingsView;
    }

    @Override
    public DetailView getDetailInputView() {
        return new DetailDialog(mainFrame, getTextComponentWrapperFactory(), dialogs);
    }

    @Override
    public StationInputView getStationInputView() {
        return new StationInputDialog(mainFrame, dialogs);
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public Dialogs getDialogs() {
        return dialogs;
    }

    @Override
    public DetailSelectionView getDetailSelectionView() {
        return detailSelectionView;
    }

    /**
     * @return the dxfSettingsView
     */
    @Override
    public DXFSettingsView getDXFSettingsView() {
        return dxfSettingsView;
    }

    /**
     * @return the newsView
     */
    @Override
    public NewsView getNewsView() {
        return newsView;
    }

    @Override
    public LogoView getLogoView() {
        return logoView;
    }

    /**
     * @return the problemsView
     */
    @Override
    public ProblemsView getProblemsView() {
        return problemsView;
    }

    /**
     * @return the dxfSettingsView
     */
    public DXFSettingsView getDxfSettingsView() {
        return dxfSettingsView;
    }

    /**
     * @return the dxfCompleteView
     */
    @Override
    public DXFCompleteView getDXFCompleteView() {
        return dxfCompleteView;
    }

    /**
     * @return the editHistoryView
     */
    @Override
    public EditHistoryView getEditHistoryView() {
        return editHistoryView;
    }

    @Override
    public FileManager getFileManager(ProblemsPresenter problemsPresenter) {
        FileManager fileManager = new FileManagerImpl(dialogs, mainFrame, dxfSettingsView, problemsPresenter,
                getDateAndTimeParserFactory(), getFormatter());
        fileManager.setClientStorage(getClientStorage());
        return fileManager;
    }

    @Override
    public DateAndTimeParserFactory getDateAndTimeParserFactory() {
        if (dateAndTimeParserFactory == null) {
            dateAndTimeParserFactory = new JavaDateAndTimeParserFactory();
        }
        return dateAndTimeParserFactory;
    }

    @Override
    public DxfFactory getDxfFactory() {
        return new DxfFactory(new JavaFormatter());
    }

    @Override
    public Scheduler getScheduler() {
        if (scheduler == null) {
            scheduler = code -> SwingUtilities.invokeLater(code);
        }
        return scheduler;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        if (resourceLoader == null) {
            resourceLoader = new ResourceLoader();
        }
        return resourceLoader;
    }

    @Override
    public JSONSystem getJSONSystem() {
        return new JSONSystemServer();
    }

    @Override
    public MailSender getMailSender() {
        if (mailSender == null) {
            mailSender = new ServerMailSender();
        }
        return mailSender;
    }

    @Override
    public Formatter getFormatter() {
        if (formatter == null) {
            formatter = new JavaFormatter();
        }
        return formatter;
    }

    @Override
    public TextComponentWrapperFactory getTextComponentWrapperFactory() {
        return new SwingTextComponentWrapperFactory();
    }

    @Override
    public SpiffyTimer getTimer() {
        return new SwingTimer();
    }

    @Override
    public ClientStorage getClientStorage() {
        if (clientStorage == null) {
            clientStorage = new JavaClientStorage(STORAGE_FILE);
        }
        return clientStorage;
    }

    @Override
    public HelpView getHelpView() {
        return helpView;
    }

    /**
     * @return the traverseInputView
     */
    @Override
    public TraverseInputView getTraverseInputView() {
        return traverseInputView;
    }

    /**
     * @return the codesView
     */
    @Override
    public CodesView getCodesView() {
        return codesView;
    }

    @Override
    public TraverseDisplayView getTraverseDisplayView() {
        return traverseDisplayView;
    }

    @Override
    public PrinterSettings getPrinterSettings() {
        if (printerSettings == null) {
            printerSettings = new AWTPrinterSettings();
        }
        return printerSettings;
    }

}
