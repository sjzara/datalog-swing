package com.spiffymap.datalog.ui.mvp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author steve
 */
public class ImageAnimator {
    
    private final static int INTERVAL = 1000;
    private final List<BufferedImage> images = new ArrayList<>();
    private static final Logger LOG = Logger.getLogger(ImageAnimator.class.getName());
    private Timer timer;
    private final AtomicInteger count = new AtomicInteger();
    private final AtomicInteger currentImageIndex = new AtomicInteger();
    private JButton component;
    
    private void loadImages(String... imageUrls) {
        images.clear();
        for (String imageUrl : imageUrls) {
            try (InputStream in = ImageAnimator.class.getResourceAsStream(imageUrl)) {
                BufferedImage image = ImageIO.read(in);
                images.add(image);
            } catch (IOException ioe) {
                LOG.log(Level.WARNING, "Can't load image {0}", imageUrl);
            }
        }
    }
    
    private synchronized int getImagesSize() {
        return images.size();
    }
    
    private void timerTask() {
        int imagesSize = getImagesSize();
        int index = currentImageIndex.getAndIncrement();
        double opacity = index * 1.0 / imagesSize;
        if (index == imagesSize) {
            index = 0;
            currentImageIndex.set(0);
        }
        BufferedImage baseImage = images.get(index);
        int nextIndex = index + 1;
        if (nextIndex == imagesSize) {
            nextIndex = 0;
        }
        BufferedImage nextImage = images.get(nextIndex);
        try {
            SwingUtilities.invokeAndWait(() -> {
                component.setIcon(new ImageIcon(baseImage));
            });
            if (count.getAndIncrement() == 100) {
                timer.cancel();
            }
        } catch (InterruptedException ie) {
            timer.cancel();
        } catch (InvocationTargetException ite) {
            LOG.log(Level.WARNING, ite.getMessage());
            timer.cancel();
        }
    }
    
    private synchronized void animate() {
        if (timer != null) {
            timer.cancel();
        }        
        timer = new Timer();
        currentImageIndex.set(0);
        count.set(0);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerTask();
            }
        }, 1000, INTERVAL);
    }
    
    public void animate(JButton component, String... imageUrls) {
        this.component = component;
        loadImages(imageUrls);
        animate();
    }
}
