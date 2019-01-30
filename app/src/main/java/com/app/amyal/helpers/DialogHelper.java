package com.app.amyal.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;

public class DialogHelper {
    private Dialog dialog;
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }

    public Dialog initForgotPasswordDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnOk = (AnyTextView) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(onclicklistener);
        return this.dialog;
    }

   /* public Dialog initRedeemedCouponDialog(int layoutID, View.OnClickListener onokclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnOk);
        btnSubmit.setOnClickListener(onokclicklistener);

        return this.dialog;
    }*/

    public Dialog initAddReviewDialog(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);

        final AnyEditTextView edtReview = (AnyEditTextView) dialog.findViewById(R.id.edtReview);

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onokclicklistener);

        ImageView btnCLose = (ImageView) dialog.findViewById(R.id.btnCLose);
        btnCLose.setOnClickListener(oncancelclicklistener);

        return this.dialog;
    }

    public Dialog initLoginAlertDialog(int layoutID, View.OnClickListener onokclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(onokclicklistener);

        return this.dialog;
    }

  /*  public Dialog initCountryCodeDialog(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onokclicklistener);
        AnyTextView btnCancel = (AnyTextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(oncancelclicklistener);

        return this.dialog;
    }*/



   /* public Dialog initLogoutDialog(int layoutID, View.OnClickListener onyesclicklistener, View.OnClickListener onnoclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(onyesclicklistener);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(onnoclicklistener);

        return this.dialog;
    }*/

    public Dialog initlogout(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initTravellerDialog(int layoutID, View.OnClickListener onDoneClicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        final AnyTextView tvNoOfAdult = (AnyTextView) dialog.findViewById(R.id.tvNoOfAdult);
        final AnyTextView tvNoOfChild = (AnyTextView) dialog.findViewById(R.id.tvNoOfChild);
        final AnyTextView tvNoOfInfant = (AnyTextView) dialog.findViewById(R.id.tvNoOfInfant);
        AnyTextView tvDone = (AnyTextView) dialog.findViewById(R.id.tvDone);
        tvDone.setOnClickListener(onDoneClicklistener);

        ImageButton btnAdultAdd = (ImageButton) dialog.findViewById(R.id.btnAdultAdd);
        btnAdultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int No = Integer.parseInt(tvNoOfAdult.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfAdult.setText(No+"");
                }


            }
        });

        ImageButton btnChildAdd = (ImageButton) dialog.findViewById(R.id.btnChildAdd);
        btnChildAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfChild.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfChild.setText(No+"");
                }
            }
        });

        ImageButton btnInfantdAdd = (ImageButton) dialog.findViewById(R.id.btnInfantdAdd);
        btnInfantdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfInfant.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfInfant.setText(No+"");
                }
            }
        });

        ImageButton btnAdultRemove = (ImageButton) dialog.findViewById(R.id.btnAdultRemove);
        btnAdultRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfAdult.getText().toString());

                if(No > 1){
                    No--;
                    tvNoOfAdult.setText(No+"");
                }
            }
        });

        ImageButton btnChildRemove = (ImageButton) dialog.findViewById(R.id.btnChildRemove);
        btnChildRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfChild.getText().toString());

                if(No > 0){
                    No--;
                    tvNoOfChild.setText(No+"");
                }
            }
        });

        ImageButton btnInfantRemove = (ImageButton) dialog.findViewById(R.id.btnInfantRemove);
        btnInfantRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfInfant.getText().toString());

                if(No > 0){
                    No--;
                    tvNoOfInfant.setText(No+"");
                }
            }
        });

        return this.dialog;
    }

    public Dialog initHotelDialog(int layoutID, View.OnClickListener onDoneClicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        final AnyTextView tvNoOfAdult = (AnyTextView) dialog.findViewById(R.id.tvNoOfAdult);
        final AnyTextView tvNoOfChild = (AnyTextView) dialog.findViewById(R.id.tvNoOfChild);
        final AnyTextView tvNoOfInfants = (AnyTextView) dialog.findViewById(R.id.tvNoOfInfants);
        AnyTextView tvDone = (AnyTextView) dialog.findViewById(R.id.tvDone);
        tvDone.setOnClickListener(onDoneClicklistener);

        ImageButton btnAdultAdd = (ImageButton) dialog.findViewById(R.id.btnAdultAdd);
        btnAdultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int No = Integer.parseInt(tvNoOfAdult.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfAdult.setText(No+"");
                }


            }
        });

        ImageButton btnChildAdd = (ImageButton) dialog.findViewById(R.id.btnChildAdd);
        btnChildAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfChild.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfChild.setText(No+"");
                }
            }
        });

        ImageButton btnRoomAdd = (ImageButton) dialog.findViewById(R.id.btnRoomAdd);
        btnRoomAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfInfants.getText().toString());

                if(No <= 100){
                    No++;
                    tvNoOfInfants.setText(No+"");
                }
            }
        });

        ImageButton btnAdultRemove = (ImageButton) dialog.findViewById(R.id.btnAdultRemove);
        btnAdultRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfAdult.getText().toString());

                if(No > 1){
                    No--;
                    tvNoOfAdult.setText(No+"");
                }
            }
        });

        ImageButton btnChildRemove = (ImageButton) dialog.findViewById(R.id.btnChildRemove);
        btnChildRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfChild.getText().toString());

                if(No > 0){
                    No--;
                    tvNoOfChild.setText(No+"");
                }
            }
        });

        ImageButton btnRoomRemove = (ImageButton) dialog.findViewById(R.id.btnRoomRemove);
        btnRoomRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No = Integer.parseInt(tvNoOfInfants.getText().toString());

                if(No > 0){
                    No--;
                    tvNoOfInfants.setText(No+"");
                }
            }
        });

        return this.dialog;
    }

    public String getTravellerInfo(int id){

        AnyTextView anyEditTextView = (AnyTextView) dialog.findViewById(id);

        String text =  anyEditTextView.getText().toString();
        if(text== null)
            text = "0";

        return text;
    }

    public String getReview(int id){

        AnyEditTextView anyEditTextView = (AnyEditTextView) dialog.findViewById(id);

        String text =  anyEditTextView.getText().toString();

        if(text== null)
            text = "";

        return text;
    }

    public String getRatting(int id){

        CustomRatingBar customRatingBar = (CustomRatingBar) dialog.findViewById(id);

        String text =  customRatingBar.getScore()+"";

        if(text== null)
            text = "1.0";

        return text;
    }

    public void showDialog() {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        dialog.dismiss();
    }

}
