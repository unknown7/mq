package com.mq.service;

public interface StatisticsService {

    void watchVideo(String skey, Long id);
    void purchaseVideo(String skey, Long id);
    void accessVideo(String skey, Long id);
}
