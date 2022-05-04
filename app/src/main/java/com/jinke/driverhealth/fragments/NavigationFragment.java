package com.jinke.driverhealth.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.navigation.PolicyActivity;
import com.jinke.driverhealth.interfaces.ApplyPermissionCallback;
import com.jinke.driverhealth.utils.PermissionUtil;


public class NavigationFragment extends Fragment {

    private AmapNaviPage mInstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE

        }, 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        CardView c1 = view.findViewById(R.id.navigation_11),
                c2 = view.findViewById(R.id.policy);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyForPermission(getActivity());
                initNav(getActivity());
//                startActivity(new Intent(getActivity(), NavActivity.class));
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PolicyActivity.class));
            }
        });

        return view;
    }


    private void initNav(FragmentActivity activity) {

        MapsInitializer.updatePrivacyShow(activity, true, true);
        MapsInitializer.updatePrivacyAgree(activity, true);

        AmapNaviParams params = new AmapNaviParams(null, null, null, AmapNaviType.DRIVER);
        params.setUseInnerVoice(true);
        AmapNaviPage.getInstance().showRouteActivity(activity.getApplicationContext(), params, new mINav());
    }

    /**
     * android6.0 以后 必须 动态 申请通讯录权限
     */
    private void applyForPermission(FragmentActivity activity) {
        String[] permissList = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
               };
        PermissionUtil.addPermissByPermissionList(activity, permissList, 0, new ApplyPermissionCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void success() {
                initNav(activity);
            }

            @Override
            public void fail() {
                //TODO 拒绝授权
            }
        });
    }

    private class mINav implements INaviInfoCallback {

        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onArriveDestination(boolean b) {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onStopSpeaking() {

        }

        @Override
        public void onReCalculateRoute(int i) {

        }

        @Override
        public void onExitPage(int i) {

        }

        @Override
        public void onStrategyChanged(int i) {

        }

        @Override
        public void onArrivedWayPoint(int i) {

        }

        @Override
        public void onMapTypeChanged(int i) {

        }

        @Override
        public void onNaviDirectionChanged(int i) {

        }

        @Override
        public void onDayAndNightModeChanged(int i) {

        }

        @Override
        public void onBroadcastModeChanged(int i) {

        }

        @Override
        public void onScaleAutoChanged(boolean b) {

        }

        @Override
        public View getCustomMiddleView() {
            return null;
        }

        @Override
        public View getCustomNaviView() {
            return null;
        }

        @Override
        public View getCustomNaviBottomView() {
            return null;
        }
    }


}