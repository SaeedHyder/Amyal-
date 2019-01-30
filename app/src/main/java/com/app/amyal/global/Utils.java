package com.app.amyal.global;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.activities.DockActivity;
import com.app.amyal.helpers.DateHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    public static int setColor(Context context, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

        {
            return context.getColor(colorId);
        } else

        {
            //noinspection deprecation
            return context.getResources().getColor(colorId);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void HideKeyBoard(DockActivity dockActivity) {
        View view = dockActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) dockActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isNumberValid(String number) {
        String regExpn = "[+][0-9]";

        CharSequence inputStr = number;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

   /* public static String getUserId(BasePreferenceHelper prefHelper){

        if(prefHelper.getUser() != null){

            return prefHelper.getUser().getId()+"";

        }else{
            return "0";
        }
    }*/

    /*public static boolean isUserLogin(DockActivity activity ,BasePreferenceHelper prefHelper){

        if(prefHelper.isLogin()){

            return true;

        }else{
            UIHelper.showLongToastInCenter(activity, activity.getString(R.string.pleaseLoginFirst));
            return false;
        }
    }*/

    public static String getLocalTimeDate(String OurDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(OurDate);

            OurDate = DateHelper.dateFormat(value.toString(), DateHelper.DATE_TIME_FORMAT, "EEE MMM dd HH:mm:ss zzz yyyy");

        } catch (Exception e) {
            OurDate = "0000-00-00 00:00";
        }
        return OurDate;
    }

  /* public static String getBadgeCount(BasePreferenceHelper prefHelper){

       try {
           List<MyCartEnt> myCartEntList = prefHelper.getMyCart();
           if (myCartEntList != null) {
               return myCartEntList.size() +"";
           }
       }catch (Exception e){
           e.printStackTrace();
       }

       return "";

    }

    public static int getBadgeCountInt(BasePreferenceHelper prefHelper){

        try {
            List<MyCartEnt> myCartEntList = prefHelper.getMyCart();
            if (myCartEntList != null) {
                return myCartEntList.size();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }*/

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static void justifyListViewHeightBasedOnChildren(GridView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static void justifyListViewHeightBasedOnChildrenNew(GridView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > 2) {
            x = items / 2;
            rows = (int) (x + 7);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }

    public static String getTextRatting(float ratting) {

        if (ratting > 0.0 && ratting == 1.0) {
            return AppConstants.Poor;
        } else if (ratting > 1.0 && ratting == 2.0) {
            return AppConstants.Fair;
        } else if (ratting > 2.0 && ratting == 3.0) {
            return AppConstants.Good;
        } else if (ratting > 3.0 && ratting == 4.0) {
            return AppConstants.VeryGood;
        } else if (ratting > 4.0 && ratting == 5.0) {
            return AppConstants.Excellent;
        }

        return "";
    }

    public static String getIcon(String amenity) {

        String image = "";

        switch (amenity) {

            case "Living room":
                image = "drawable://" + R.drawable.room;
                break;

            case "Number of bedrooms":
                image = "drawable://" + R.drawable.bed;
                break;

            case "Wheelchair-accessible":
                image = "drawable://" + R.drawable.wheelchair;
                break;

            case "Disability-friendly bathroom":
                image = "drawable://" + R.drawable.wheelchair;
                break;

            case "Room size (sqm)":
                image = "drawable://" + R.drawable.roomsize;
                break;

            case "Extra beds on demand":
                image = "drawable://" + R.drawable.bed;
                break;

            case "Internet access":
                image = "drawable://" + R.drawable.wifi;
                break;

            case "Wi-fi":
                image = "drawable://" + R.drawable.wifi;
                break;

            case "Smoking rooms":
                image = "drawable://" + R.drawable.smoking;
                break;

            case "Bathrobes":
                image = "drawable://" + R.drawable.bathoders;
                break;

            case "Mini fridge":
                image = "drawable://" + R.drawable.fridge;
                break;

            case "Shower":
                image = "drawable://" + R.drawable.shower;
                break;

            case "Hairdryer":
                image = "drawable://" + R.drawable.hairdryer;
                break;

            case "Satellite TV":
                image = "drawable://" + R.drawable.led;
                break;

            case "Safe":
                image = "drawable://" + R.drawable.saftey;
                break;

            case "Cable TV":
                image = "drawable://" + R.drawable.led;
                break;

            case "Direct dial telephone":
                image = "drawable://" + R.drawable.phone;
                break;

            case "Make-up mirror":
                image = "drawable://" + R.drawable.wifi;
                break;

            case "Centrally regulated air conditioning":
                image = "drawable://" + R.drawable.ac;
                break;

            case "Tea and coffee making facilities":
                image = "drawable://" + R.drawable.wifi;
                break;

            case "Bathtub":
                image = "drawable://" + R.drawable.personal_bath;
                break;

            case "Carpeted floors":
                image = "drawable://" + R.drawable.wifi;
                break;

            case "Minibar":
                image = "drawable://" + R.drawable.minbar;
                break;

            case "Toiletries":
                image = "drawable://" + R.drawable.toileteries;
                break;

            case "Slippers":
                image = "drawable://" + R.drawable.slippers;
                break;

            case "Fridge":
                image = "drawable://" + R.drawable.fridge;
                break;

            case "Kitchen":
                image = "drawable://" + R.drawable.kitchen;
                break;

            case "Cable/Satellite TV":
                image = "drawable://" + R.drawable.led;
                break;

            case "Iron & Ironing Board":
                image = "drawable://" + R.drawable.ironstand;
                break;

            case "Telephone + Voice Mail":
                image = "drawable://" + R.drawable.phone;
                break;

            case "Private Bath":
                image = "drawable://" + R.drawable.personal_bath;
                break;

            case "AM/FM Clock":
                image = "drawable://" + R.drawable.amentities;
                break;

            case "Wake-Up Service":
                image = "drawable://" + R.drawable.wakeup;
                break;

            case "Air  Condition":
                image = "drawable://" + R.drawable.ac;
                break;

            case "Bathroom":
                image = "drawable://" + R.drawable.bathroom;
                break;

            case "Ironing set":
                image = "drawable://" + R.drawable.iron;
                break;

            default:
                image = "drawable://" + R.drawable.amentities;
                break;
        }

        return image;
    }

    public static String getFilePath(DockActivity inContext, Bitmap inImage) {
        String selectedImagePath;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Uri uri = Uri.parse(path);
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = inContext.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        } else {
            selectedImagePath = null;
        }

        if (selectedImagePath == null) {
            // 2:OI FILE Manager --- call method: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;


    }

    public static void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;

        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String getCarTypeCode(int position) {

        String carTypeCode = "";

        switch (position) {

            case 0:
                carTypeCode = "";
                break;

            case 1:
                carTypeCode = "M";
                break;

            case 2:
                carTypeCode = "N";
                break;

            case 3:
                carTypeCode = "E";
                break;

            case 4:
                carTypeCode = "H";
                break;

            case 5:
                carTypeCode = "C";
                break;

            case 6:
                carTypeCode = "D";
                break;

            case 7:
                carTypeCode = "I";
                break;

            case 8:
                carTypeCode = "J";
                break;

            case 9:
                carTypeCode = "S";
                break;

            case 10:
                carTypeCode = "R";
                break;

            case 11:
                carTypeCode = "F";
                break;

            case 12:
                carTypeCode = "G";
                break;

            case 13:
                carTypeCode = "P";
                break;

            case 14:
                carTypeCode = "U";
                break;

            case 15:
                carTypeCode = "L";
                break;

            case 16:
                carTypeCode = "W";
                break;

            case 17:
                carTypeCode = "O";
                break;

            case 18:
                carTypeCode = "X";
                break;

            default:
                carTypeCode = "";
                break;

        }

        return carTypeCode;
    }

    public static String getAge(String year, String month, String day) {


        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public String formatNumber(int decimals, double number) {
        StringBuilder sb = new StringBuilder(decimals + 2);
        sb.append("#.");
        for (int i = 0; i < decimals; i++) {
            sb.append("0");
        }
        return new DecimalFormat(sb.toString()).format(number);
    }

    public File FileFromUrl(String imageUrl, Context context) throws IOException {
        File fileForImage = new File(Environment.getExternalStorageDirectory() + "/image.jpg");
        if (!fileForImage.exists()) {
            fileForImage.createNewFile();
        }
        InputStream sourceStream;
        File cachedImage = ImageLoader.getInstance().getDiscCache().get(imageUrl);
        if (cachedImage != null && cachedImage.exists()) { // if image was cached by UIL
            sourceStream = new FileInputStream(cachedImage);
        } else { // otherwise - download image
            ImageDownloader downloader = new BaseImageDownloader(context);
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

}
