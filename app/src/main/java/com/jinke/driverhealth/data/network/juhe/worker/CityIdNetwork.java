package com.jinke.driverhealth.data.network.juhe.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.juhe.api.CityIdService;
import com.jinke.driverhealth.data.network.juhe.beans.CityId;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/5/3
 */
public class CityIdNetwork {
    private ServiceCreator serviceCreator = new ServiceCreator("https://www.fastmock.site/mock/13b68abfde80ac3c44fd1fc8d8c8d044/cityid/");
    private CityIdService mCityIdService = serviceCreator.create(CityIdService.class);

    public void requestAoMenCityIdData(Callback<CityId> callback) {
        mCityIdService.getAoMenCityId().enqueue(callback);
    }

    public void requestXiangGangCityIdData(Callback<CityId> callback) {
        mCityIdService.getXiangGangCityId().enqueue(callback);
    }

    public void requestTaiWanCityIdData(Callback<CityId> callback) {
        mCityIdService.getTaiWanCityId().enqueue(callback);
    }

    public void requestZhejiangCityIdData(Callback<CityId> callback) {
        mCityIdService.getZhejiangCityId().enqueue(callback);
    }

    public void requestYunNanCityIdData(Callback<CityId> callback) {
        mCityIdService.getYunNanCityId().enqueue(callback);
    }

    public void requestXiZangCityIdData(Callback<CityId> callback) {
        mCityIdService.getXiZangCityId().enqueue(callback);
    }

    public void requestXinJiangCityIdData(Callback<CityId> callback) {
        mCityIdService.getXinJiangCityId().enqueue(callback);
    }

    public void requestTianJinCityIdData(Callback<CityId> callback) {
        mCityIdService.getTianJinCityId().enqueue(callback);
    }

    public void requestShanXiXiCityIdData(Callback<CityId> callback) {
        mCityIdService.getShanXiXiCityId().enqueue(callback);
    }

    public void requestShanXiDongCityIdData(Callback<CityId> callback) {
        mCityIdService.getShanXiDongCityId().enqueue(callback);
    }

    public void requestShangHaiCityIdData(Callback<CityId> callback) {
        mCityIdService.getShangHaiCityId().enqueue(callback);
    }

    public void requestShanDongCityIdData(Callback<CityId> callback) {
        mCityIdService.getShanDongCityId().enqueue(callback);
    }

    public void requestSiChuanCityIdData(Callback<CityId> callback) {
        mCityIdService.getSiChuanCityId().enqueue(callback);
    }

    public void requestQingHaiCityIdData(Callback<CityId> callback) {
        mCityIdService.getQingHaiCityId().enqueue(callback);
    }

    public void requestNingXiaCityIdData(Callback<CityId> callback) {
        mCityIdService.getNingXiaCityId().enqueue(callback);
    }

    public void requestNeiMengGuCityIdData(Callback<CityId> callback) {
        mCityIdService.getNeiMengGuCityId().enqueue(callback);
    }

    public void requestLiaoNingCityIdData(Callback<CityId> callback) {
        mCityIdService.getLiaoNingCityId().enqueue(callback);
    }

    public void requestJiangXiCityIdData(Callback<CityId> callback) {
        mCityIdService.getJiangXiCityId().enqueue(callback);
    }

    public void requestJiangSuCityIdData(Callback<CityId> callback) {
        mCityIdService.getJiangSuCityId().enqueue(callback);
    }

    public void requestJiLinCityIdData(Callback<CityId> callback) {
        mCityIdService.getJiLinCityId().enqueue(callback);
    }

    public void requestHuNanCityIdData(Callback<CityId> callback) {
        mCityIdService.getHuNanCityId().enqueue(callback);
    }

    public void requestHeNanCityIdData(Callback<CityId> callback) {
        mCityIdService.getHeNanCityId().enqueue(callback);
    }

    public void requestHaiNanCityIdData(Callback<CityId> callback) {
        mCityIdService.getHaiNanCityId().enqueue(callback);
    }

    public void requestHeiLongJiangCityIdData(Callback<CityId> callback) {
        mCityIdService.getHeiLongJiangCityId().enqueue(callback);
    }

    public void requestHuBeiCityIdData(Callback<CityId> callback) {
        mCityIdService.getHuBeiCityId().enqueue(callback);
    }

    public void requestHeBeiCityIdData(Callback<CityId> callback) {
        mCityIdService.getHeBeiCityId().enqueue(callback);
    }

    public void requestGuiZhouCityIdData(Callback<CityId> callback) {
        mCityIdService.getGuiZhouCityId().enqueue(callback);
    }

    public void requestGanSuCityIdData(Callback<CityId> callback) {
        mCityIdService.getGanSuCityId().enqueue(callback);
    }

    public void requestGuangXiCityIdData(Callback<CityId> callback) {
        mCityIdService.getGuangXiCityId().enqueue(callback);
    }

    public void requestGuangDongCityIdData(Callback<CityId> callback) {
        mCityIdService.getGuangDongCityId().enqueue(callback);
    }

    public void requestFuJianCityIdData(Callback<CityId> callback) {
        mCityIdService.getFuJianCityId().enqueue(callback);
    }

    public void requestChongQingCityIdData(Callback<CityId> callback) {
        mCityIdService.getChongQingCityId().enqueue(callback);
    }

    public void requestBeiJingCityIdData(Callback<CityId> callback) {
        mCityIdService.getBeiJingCityId().enqueue(callback);
    }

    public void requestAnHuiCityIdData(Callback<CityId> callback) {
        mCityIdService.getAnHuiCityId().enqueue(callback);
    }


}
