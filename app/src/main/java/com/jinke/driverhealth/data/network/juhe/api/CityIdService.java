package com.jinke.driverhealth.data.network.juhe.api;

import com.jinke.driverhealth.data.network.juhe.beans.CityId;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author: fanlihao
 * @date: 2022/5/3
 */
public interface CityIdService {
    //澳门
    @GET("api/aomen")
    Call<CityId> getAoMenCityId();

    //香港
    @GET("api/xianggang")
    Call<CityId> getXiangGangCityId();

    //台湾
    @GET("api/taiwan")
    Call<CityId> getTaiWanCityId();

    //浙江
    @GET("api/zhejiang")
    Call<CityId> getZhejiangCityId();

    //云南
    @GET("api/yunnan")
    Call<CityId> getYunNanCityId();

    //西藏
    @GET("api/xizang")
    Call<CityId> getXiZangCityId();

    //新疆
    @GET("api/xinjiang")
    Call<CityId> getXinJiangCityId();

    //天津
    @GET("api/tianjin")
    Call<CityId> getTianJinCityId();

    //陕西
    @GET("api/shanxixi")
    Call<CityId> getShanXiXiCityId();

    //山西
    @GET("api/shanxidong")
    Call<CityId> getShanXiDongCityId();

    //上海
    @GET("api/shanghai")
    Call<CityId> getShangHaiCityId();

    //山东
    @GET("api/shandong")
    Call<CityId> getShanDongCityId();

    //四川
    @GET("api/sichuan")
    Call<CityId> getSiChuanCityId();

    //青海
    @GET("api/qinghai")
    Call<CityId> getQingHaiCityId();

    //宁夏
    @GET("api/ningxia")
    Call<CityId> getNingXiaCityId();

    //内蒙古
    @GET("api/neimenggu")
    Call<CityId> getNeiMengGuCityId();

    //辽宁
    @GET("api/liaoning")
    Call<CityId> getLiaoNingCityId();

    //江西
    @GET("api/jiangxi")
    Call<CityId> getJiangXiCityId();

    //江苏
    @GET("api/jiangsu")
    Call<CityId> getJiangSuCityId();

    //吉林
    @GET("aip/jilin")
    Call<CityId> getJiLinCityId();

    //湖南
    @GET("api/hunan")
    Call<CityId> getHuNanCityId();

    //河南
    @GET("api/henan")
    Call<CityId> getHeNanCityId();

    //海南
    @GET("api/hainan")
    Call<CityId> getHaiNanCityId();

    //黑龙江
    @GET("api/heilongjiang")
    Call<CityId> getHeiLongJiangCityId();

    //湖北
    @GET("api/hubei")
    Call<CityId> getHuBeiCityId();

    //河北
    @GET("api/hebei")
    Call<CityId> getHeBeiCityId();

    //贵州
    @GET("api/guizhou")
    Call<CityId> getGuiZhouCityId();

    //甘肃
    @GET("api/gansu")
    Call<CityId> getGanSuCityId();

    //广西
    @GET("api/guangxi")
    Call<CityId> getGuangXiCityId();

    //广东
    @GET("api/guangdong")
    Call<CityId> getGuangDongCityId();

    //福建
    @GET("api/fujian")
    Call<CityId> getFuJianCityId();

    //重庆
    @GET("api/chongqing")
    Call<CityId> getChongQingCityId();

    //北京
    @GET("api/beijing")
    Call<CityId> getBeiJingCityId();

    //	安徽
    @GET("api/anhui")
    Call<CityId> getAnHuiCityId();

}
