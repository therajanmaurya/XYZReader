package com.opensource.xyz.reader.ui.main;

import com.opensource.xyz.reader.ui.base.MvpView;

import java.util.List;

import com.opensource.xyz.reader.data.model.Ribot;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
