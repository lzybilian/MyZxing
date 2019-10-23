package com.skateboard.zxinglib.help;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.skateboard.zxinglib.DecodeFormatManager;

import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class ScanImageUtils {
    static final Set<BarcodeFormat> INDUSTRIAL_FORMATS;
    static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    static {
        INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR);
    }
    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public static Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.addAll(INDUSTRIAL_FORMATS);
            decodeFormats.addAll(QR_CODE_FORMATS);
            decodeFormats.addAll(DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        Bitmap scanBitmap = getScanBitmap(path);
        if (scanBitmap == null) return null;
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final int REQUST_WIDTH = 1280;
    private static final int REQUST_HEIGHT = 1080;
    private static Bitmap getScanBitmap(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap scanBitmap = BitmapFactory.decodeFile(photoPath, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;
        if (height > REQUST_HEIGHT || width > REQUST_WIDTH) {
            int heightRatio = Math.round((float) height
                    / (float) REQUST_HEIGHT);
            int widthRatio = Math.round((float) width / (float) REQUST_WIDTH);
            sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        scanBitmap = BitmapFactory.decodeFile(photoPath, options);
        return scanBitmap;
    }
}
