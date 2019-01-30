package com.app.amyal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SignupFragment extends BaseFragment implements ImageSetter, ActivityResultInterface {

    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.edtName)
    AnyEditTextView edtName;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.cbTerms)
    CheckBox cbTerms;
    @BindView(R.id.txtTerms)
    AnyTextView txtTerms;
    @BindView(R.id.btnSignup)
    Button btnSignup;
    Unbinder unbinder;

    File profilePic;
    String profilePath = "";
    @BindView(R.id.civProfilePic1)
    CircleImageView civProfilePic1;
    @BindView(R.id.civProfilePic)
    CircleImageView civProfilePic;

    boolean isCameFromBuyFlow = false;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.edtCity)
    AnyEditTextView edtCity;
    @BindView(R.id.edtPostalCode)
    AnyEditTextView edtPostalCode;
    @BindView(R.id.edtAddress)
    AnyEditTextView edtAddress;
    @BindView(R.id.llPassword)
    LinearLayout llPassword;
    @BindView(R.id.viewPassword)
    View viewPassword;
    @BindView(R.id.llconfirm)
    LinearLayout llconfirm;
    @BindView(R.id.viewConfirm)
    View viewConfirm;
    @BindView(R.id.ccpCode)
    CountryCodePicker ccpCode;

    private String mSocialMediaPlatform = "";
    private String mFacebookSocialMediaID = "";
    private String mGoogleSocialMediaID = "";
    private String mTwitterSocialMediaID = "";
    private String Name = "";
    private String Email = "";

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    public void setIsCameFromBuyFlow(boolean isCameFromBuyFlow) {
        this.isCameFromBuyFlow = isCameFromBuyFlow;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setImageSetter(this);
        getMainActivity().setOnActivityResultInterface(this);

        if (mSocialMediaPlatform != null && mSocialMediaPlatform.length() > 0) {
            edtName.setText(Name);
            edtEmail.setText(Email);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.signup));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        getMainActivity().setBackground(R.drawable.sp_light);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {

            llconfirm.setVisibility(View.GONE);
            llPassword.setVisibility(View.GONE);
            viewConfirm.setVisibility(View.GONE);
            viewPassword.setVisibility(View.GONE);

            mSocialMediaPlatform = getArguments().getString(AppConstants.SocialMediaPlatform);
            if (mSocialMediaPlatform != null && mSocialMediaPlatform.length() > 0) {
                Name = getArguments().getString(AppConstants.Name);
                Email = getArguments().getString(AppConstants.Email);
                if (getArguments().getString(AppConstants.ProfileImage) != null)
                    profilePath = getArguments().getString(AppConstants.ProfileImage);

                if (mSocialMediaPlatform.equalsIgnoreCase(WebServiceConstants.PLATFORM_GOOGLE)) {
                    mFacebookSocialMediaID = getArguments().getString(AppConstants.SocialMediaId);
                } else if (mSocialMediaPlatform.equalsIgnoreCase(WebServiceConstants.PLATFORM_FACEBOOK)) {
                    mGoogleSocialMediaID = getArguments().getString(AppConstants.SocialMediaId);
                } else if (mSocialMediaPlatform.equalsIgnoreCase(WebServiceConstants.PLATFORM_TWITTER)) {
                    mTwitterSocialMediaID = getArguments().getString(AppConstants.SocialMediaId);
                }

                profilePic = new File(profilePath);
                if (profilePath.length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(profilePath)
                            .into(civProfilePic);
                }
            }
        }

        return view;

    }

    private boolean validated() {

        /*if (profilePath.length() == 0) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_profile_photo));
            return false;
        } else */
        if (!edtName.testValidity()) {
            return false;
        } else if (edtEmail.getText().toString().trim().equals("") ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.email_error));
            return false;
        } else if (llPassword.getVisibility() == View.VISIBLE && (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().length() < 6)) {
            edtPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else if (llPassword.getVisibility() == View.VISIBLE && (edtConfirmPassword.getText().toString().trim().equals(""))) {
            edtPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else if (llPassword.getVisibility() == View.VISIBLE && (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString()))) {
            edtConfirmPassword.setError(getString(R.string.confirm_pas_not_match));
            return false;
        } else if (edtPhone.getText().toString().length() == 0) {
            edtPhone.setError(getString(R.string.please_enter_phone));
            //UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_phone));
            return false;
        } else if (edtPhone.getText().toString().length() < 7 || edtPhone.getText().toString().length() > 20) {
            edtPhone.setError(getString(R.string.please_enter_valid_phone));
            //UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_valid_phone));
            return false;
        } else if (!edtCity.testValidity()) {
            return false;
        } else if (!edtPostalCode.testValidity()) {
            return false;
        } else if (!edtAddress.testValidity()) {
            return false;
        } else if (!cbTerms.isChecked()) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_accept_terms));
            return false;
        } else {
            return true;
        }

    }

    @OnClick({R.id.ivAdd, R.id.txtTerms, R.id.btnSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAdd:
                getMainActivity().cameraPermission();
                break;
            case R.id.txtTerms:
                getDockActivity().replaceDockableFragment(TermsConditionFragment.newInstance(), "TermsConditionFragment");
                break;
            case R.id.btnSignup:
                if (validated()) {

                    if (profilePath != null && profilePath.length() > 0 && profilePath.contains("http")) {
                        getMainActivity().writePermission();
                        return;
                    }

                    signupService();

                }
                break;
        }
    }

    public File FileFromUrl(String imageUrl) throws IOException {
        File fileForImage = new File(Environment.getExternalStorageDirectory() + "/image.jpg");
        if (!fileForImage.exists()) {
            fileForImage.createNewFile();
        }
        InputStream sourceStream;
        File cachedImage = ImageLoader.getInstance().getDiscCache().get(imageUrl);
        if (cachedImage != null && cachedImage.exists()) { // if image was cached by UIL
            sourceStream = new FileInputStream(cachedImage);
        } else { // otherwise - download image
            ImageDownloader downloader = new BaseImageDownloader(getContext());
            sourceStream = downloader.getStream(imageUrl, null);
        }

        if (sourceStream != null) {
            try {
                OutputStream targetStream = new FileOutputStream(fileForImage);
                try {
                    IoUtils.copyStream(sourceStream, targetStream, null);
                } finally {
                    targetStream.close();
                }
            } finally {
                sourceStream.close();
            }
        }

//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), fileForImage);
//        MultipartBody.Part image = MultipartBody.Part.createFormData("profile_picture", fileForImage.getName(), reqFile);

        return fileForImage;
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.signup:

                UserEnt userEnt = (UserEnt) result;

                prefHelper.putUser(userEnt);
                prefHelper.setLoginStatus(true);

                if (isCameFromBuyFlow) {
                    getDockActivity().popFragment();
                    getDockActivity().popFragment();
                } else {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                }

                break;
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResultI(int cameraPermission, int i, Intent data) {
        if (cameraPermission == AppConstants.CAMERA_PERMISSION) {
            CameraHelper.uploadPhotoDialog(getMainActivity());
        } else if (cameraPermission == AppConstants.EXTERNAL_STORAGE) {
            try {
                profilePic = FileFromUrl(profilePath);
                signupService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void signupService() {

        String countryCode = ccp.getSelectedCountryNameCode();
        RequestBody reqFile;
        MultipartBody.Part filePart = null;
        if (profilePic == null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            filePart = MultipartBody.Part.createFormData("Image", "", reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), profilePic);
            filePart = MultipartBody.Part.createFormData("Image", profilePic.getName(), reqFile);
        }

        String token = FirebaseInstanceId.getInstance().getToken();

        serviceHelper.enqueueCall(webService.signUp(
                RequestBody.create(MediaType.parse("text/plain"), edtName.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), ccpCode.getSelectedCountryCode() + edtPhone.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtPassword.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtConfirmPassword.getText().toString()),
                filePart,
                RequestBody.create(MediaType.parse("text/plain"), countryCode),
                RequestBody.create(MediaType.parse("text/plain"), edtPostalCode.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtCity.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtAddress.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), mFacebookSocialMediaID),
                RequestBody.create(MediaType.parse("text/plain"), mGoogleSocialMediaID),
                RequestBody.create(MediaType.parse("text/plain"), mTwitterSocialMediaID),
                RequestBody.create(MediaType.parse("text/plain"), AppConstants.Device_Type),
                RequestBody.create(MediaType.parse("text/plain"), token == null ? "" : token)
        ),WebServiceConstants.signup);

    }
}
