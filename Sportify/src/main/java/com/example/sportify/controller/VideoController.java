package com.example.sportify.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VideoController {
    @FXML
    private WebView videoWebView;

    public void initialize() {
        String videoUrl = "https://ai.invideo.io/watch/jBpTGK3-uQ_?fbclid=IwAR3S7p_LcKNInwtt2pTEb8Dtptet8cBts6Q6wJjecfbRg7KTWQwgC1eL_Cc";

        WebEngine webEngine = videoWebView.getEngine();
        webEngine.load(videoUrl);
    }
}
