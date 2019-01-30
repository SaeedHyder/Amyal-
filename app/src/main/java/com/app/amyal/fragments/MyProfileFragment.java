package com.app.amyal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.amyal.R;
import com.app.amyal.entities.UserEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.CameraHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ActivityResultInterface;
import com.app.amyal.interfaces.ImageSetter;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by waq on 11/22/2017.
 */

public class MyProfileFragment extends BaseFragment implements ImageSetter, ActivityResultInterface {

    @BindView(R.id.civProfilePic1)
    CircleImageView civProfilePic1;
    @BindView(R.id.civProfilePic)
    CircleImageView civProfilePic;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.edtName)
    AnyEditTextView edtName;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.llMyReviews)
    LinearLayout llMyReviews;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;

    boolean isEdit = false;

    File profilePic;
    String profilePath = "";

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);
        getMainActivity().setOnActivityResultInterface(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setImageSetter(this);

        setData();

    }

    public void setData() {

        UserEnt userEnt = prefHelper.getUser();

        if (userEnt != null && userEnt.getUser() != null) {

            edtName.setText(userEnt.getUser().getName());
            edtPhone.setText(userEnt.getUser().getPhone());

            if (userEnt.getUser().getImage() != null && userEnt.getUser().getImage().length() > 0) {
                profilePath = userEnt.getUser().getImage();
                profilePic = new File(profilePath);
                Picasso.with(getDockActivity())
                        .load(userEnt.getUser().getImage())
                        .into(civProfilePic);
            }

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.my_profile));
        titleBar.showMenuButton();
        titleBar.showRightButton(R.drawable.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit)
                    btnEdit.performClick();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.btnEdit, R.id.btnDone, R.id.llMyReviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEdit:

                if (isEdit) {
                    getMainActivity().cameraPermission();
                } else {
                    isEdit = true;
                    edtName.setEnabled(true);
                    edtName.setFocusableInTouchMode(true);
                    edtPhone.setEnabled(true);
                    edtPhone.setFocusableInTouchMode(true);
                    btnEdit.setBackgroundResource(R.drawable.photoadd);
                }


                MultipartBody.Part filePart = null;
                break;
            case R.id.btnDone:

                if (isEdit) {
                    if (validated()) {
                        if (profilePath != null && profilePath.contains("http")) {
                            filePart = null;
                        } else {
                            filePart = MultipartBody.Part.createFormData("Image", profilePic.getName(), RequestBody.create(MediaType.parse("image/*"), profilePic));
                        }

                        serviceHelper.enqueueCall(webService.updateProfile(
                                RequestBody.create(MediaType.parse("text/plain"), edtName.getText().toString()),
                                RequestBody.create(MediaType.parse("text/plain"), edtPhone.getText().toString()),
                                filePart,
                                "Bearer " + prefHelper.getUser().getAuthToken()
                        ), WebServiceConstants.updateProfile);

                    }
                }

                break;

            case R.id.llMyReviews:

                if (isEdit) {
                    isEdit = false;
                    edtName.setEnabled(false);
                    edtName.setFocusableInTouchMode(false);
                    edtPhone.setEnabled(false);
                    edtPhone.setFocusableInTouchMode(false);
                    btnEdit.setBackgroundResource(R.drawable.editpic);
                } else {
                    getDockActivity().replaceDockableFragment(MyReviewsFragment.newInstance(), "MyReviewsFragment");
                }

                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.updateProfile:

                UserEnt userEnt = (UserEnt) result;
                userEnt.setAuthToken(prefHelper.getUser().getAuthToken());
                prefHelper.putUser(userEnt);

                isEdit = false;
                edtName.setEnabled(false);
                edtName.setFocusableInTouchMode(false);
                edtPhone.setEnabled(false);
                edtPhone.setFocusableInTouchMode(false);
                btnEdit.setBackgroundResource(R.drawable.editpic);
                Toast.makeText(getMainActivity(),getString(R.string.profile_updated_success),Toast.LENGTH_LONG).show();
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.profile_updated_success));
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                break;
        }
    }

    private boolean validated() {

        if (profilePath.length() == 0) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_profile_photo));
            return false;
        } else if (!edtName.testValidity()) {
            return false;
        } else if (edtPhone.getText().toString().length() < 7 || edtPhone.getText().toString().length() > 20) {
            edtPhone.setError(getString(R.string.please_enter_valid_phone));
            //UIHelper.showLongToastInCenter(getDockActivity(),getString(R.string.please_enter_valid_phone));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
            profilePic = new File(imagePath);
            profilePath = imagePath;
            Picasso.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(civProfilePic);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

    }

    @Override
    public void onActivityResultI(int cameraPermission, int i, Intent data) {
        if (cameraPermission == AppConstants.CAMERA_PERMISSION) {
            CameraHelper.uploadPhotoDialog(getMainActivity());
        }
    }
}
