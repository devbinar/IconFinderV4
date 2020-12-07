package com.devbinar.iconfinderv4.Custom.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.devbinar.iconfinderv4.Custom.UI.Modals.ModalGeneral;
import com.devbinar.peticiones.EasyReq;
import com.devbinar.peticiones.EasyReqFilter;
import com.devbinar.peticiones.EasyReqLastRequest;

import java.util.ArrayList;

public class CustomEasyReqFilter extends EasyReqFilter {

    @Override
    public void Filter_response(Context context, String response, int code_request, EasyReq.Event event) {
        CustomLog.i("Filter_response",  CustomLog.getLine_debug()+response);
        event.Response(response, code_request);
    }

    String filter_error_string;
    @Override
    public void Filter_error(final Context context, final VolleyError volleyError, final int code_request, EasyReq.Event event) {
        try {
            filter_error_string = new String(volleyError.networkResponse.data,"UTF-8");
            CustomLog.i("Auto_clasificar", "Gestionar_error:---------------------------------------------------------------------------------------------"+ filter_error_string);
        } catch (Exception e) {
            filter_error_string = volleyError.toString();
            CustomLog.i("Auto_clasificar", "Gestionar_error:---------------------------------------------------------------------------------------------"+ filter_error_string);
        }
        if (Network.enabled(context)){
            Network.internet_access(context, new Network.OnInternet() {
                @Override
                public void IsOk() {
                    //report_error(context,""+code_request,"Error json", filter_error_string +"----- "+ volleyError.networkResponse.statusCode);
                }

                @Override
                public void IsNotOk() {
                    show_modal_without_internet(context);
                }
            });
        }else{
            show_modal_enabled_internet(context);
        }
    }

    void show_modal_enabled_internet(final Context context) {
        ModalGeneral.OnModalGeneral onModalGeneral = new ModalGeneral.OnModalGeneral() {
            @Override
            public void onPressTitle() {
            }

            @Override
            public void onPressMessage() {
            }

            @Override
            public void onPressBtn1() {
                ((Activity) context).startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                ((Activity) context).finish();
                System.exit(0);
            }

            @Override
            public void onPressBtn2() {
                EasyReq.enabledHistoryRequests(false);
                ArrayList<EasyReqLastRequest> easyReqLastRequests = EasyReq.getHistoryRequests();
                if (easyReqLastRequests.size() > 0){
                    for (int i = 0; i < easyReqLastRequests.size(); i++) {
                        EasyReq.ExecuteHistoryRequests(easyReqLastRequests.get(i));
                    }
                }else {
                    EasyReq.ExecuteLastRequest();
                }
                ModalGeneral.HideModalGeneral();
            }
        };
        ModalGeneral.ShowModalGeneral(context, "Sin internet", "Activa el <a href='http'>wifi</a> o <a href='http'>datos moviles</a>.", "Salir", "Reintentar", onModalGeneral);
    }

    void show_modal_without_internet(final Context context) {
        ModalGeneral.OnModalGeneral onModalGeneral = new ModalGeneral.OnModalGeneral() {
            @Override
            public void onPressTitle() {
            }

            @Override
            public void onPressMessage() {
            }

            @Override
            public void onPressBtn1() {
                ((Activity) context).startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                ((Activity) context).finish();
                System.exit(0);
            }

            @Override
            public void onPressBtn2() {
                EasyReq.enabledHistoryRequests(false);
                ArrayList<EasyReqLastRequest> easyReqLastRequests = EasyReq.getHistoryRequests();
                if (easyReqLastRequests.size() > 0){
                    for (int i = 0; i < easyReqLastRequests.size(); i++) {
                        EasyReq.ExecuteHistoryRequests(easyReqLastRequests.get(i));
                    }
                }else {
                    EasyReq.ExecuteLastRequest();
                }
                ModalGeneral.HideModalGeneral();
            }
        };
        ModalGeneral.ShowModalGeneral(context, "Sin internet", "Sin conexion a internet, verifica tu <a href='http'>wifi</a> o <a href='http'>datos moviles</a>.", "Salir", "Reintentar", onModalGeneral);
    }
}
