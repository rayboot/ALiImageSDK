package com.github.rayboot.aliimagesdk;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

/**
 * author: rayboot  Created on 15/9/10.
 * email : sy0725work@gmail.com
 */
public class ALiImageURL {

    private String url;
    private int width = -1;
    private int height = -1;
    private String w;
    private String h;
    private String Q = "90Q";
    private String l;
    private String e;
    private String p;
    private String c;
    private String advancedCrop;
    private String cropRange;
    private String circle;
    private String rounded;
    private String cropIndex;
    private String fitColor;
    private String format = ".webp";
    private String angle;
    private String autoRotate = "2o";
    private String sh;
    private String blur;
    private String brightness;
    private String contrast;
    private String infoexif;
    private String style;
    private boolean autoCircle = false;

    public ALiImageURL(String url) {
        url(url);
    }

    public void url(String url) {
        if (!url.contains("http")) {
            throw new IllegalArgumentException("Url must contains http.");
        }
        this.url = url;
    }

    public enum Range {
        TOP_LEFT(1),
        TOP_CENTER(2),
        TOP_RIGHT(3),
        CENTER_LEFT(4),
        CENTER_CENTER(5),
        CENTER_RIGHT(6),
        BOTTOM_LEFT(7),
        BOTTOM_CENTER(8),
        BOTTOM_RIGHT(9);

        private int index;

        // 构造方法
        Range(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

    /**
     * 单边固定缩略
     * hh
     * 宽度按比例处理。
     *
     * @param h 指定高度
     */
    public ALiImageURL height(int h) {
        if (h > 4096) {
            throw new IllegalArgumentException("h must in [1 , 4096].");
        }
        this.h = h + "h";
        this.height = h;
        return this;
    }

    /**
     * 单边固定缩略
     * ww
     * 高度按比例处理。
     *
     * @param w 指定宽度
     */
    public ALiImageURL width(int w) {
        if (w < 1 || w > 4096) {
            throw new IllegalArgumentException("w must in [1 , 4096].");
        }
        this.w = w + "w";
        this.width = w;
        return this;

    }

    /**
     * 决定jpg图片的绝对quality,把原图quality压到Q%
     * 如果原图quality小于指定数字，则不压缩。如果原图quality是100%，
     * 使用"90Q"会得到quality 90％的图片；如果原图quality是95%，
     * 使用“90Q”还会得到quality 90%的图片；如果原图quality是80%，
     * 使用“90Q”不会压缩，返回quality 80%的原图。
     * 只能在jpg/png效果上使用，其他格式无效果。
     * 如果一个转换url里，即指定了q和Q，按Q来处理
     *
     * @param Q [1,100]
     */
    public ALiImageURL quality(int Q) {
        if (Q < 1 || Q > 100) {
            throw new IllegalArgumentException("q must in [1 , 100]");
        }
        this.Q = Q + "Q";
        return this;
    }

    /**
     * 缩略
     * ww_hh
     *
     * @param w 指定目标缩略图的宽度 [1,4096]
     * @param h 指定目标缩略图的高度 [1,4096]
     */
    public ALiImageURL resize(int w, int h) {
        if (this.width < 0 || this.height < 0) {
            height(h);
            width(w);
        }
        if (autoCircle) {
            this.circle = Math.min(width / 2, height / 2) + "-" + "1ci";
        }
        return this;
    }

    /**
     * 目标缩略图大于原图是否处理
     * 1l  or  0l
     *
     * @param deal 值是1, 即不处理，是0，表示处理. 默认0-处理
     */
    public ALiImageURL biggerOpt(boolean deal) {
        this.l = (deal ? 0 : 1) + "l";
        return this;
    }

    /**
     * 缩放策略
     * <p/>
     * 默认值：0：长边（默认值）
     * 由于图片缩放过程中，原图尺寸与缩放尺寸不一定是相同比例，需要我们指定以长边还是短边优先进行缩放，
     * 如原图200 * 400（比例1:2），需要缩放为100 * 100（比例1:1）.
     * 长边优先时，缩放为50 100；短边优先时(e=1)，缩放为`100 200`，若不特别指定，则代表长边优先
     * <p/>
     * 关于“长边”和“短边”的定义需要特别注意
     * 它们表达的是在缩放中相对比例的长或短。
     * “长边”是指原尺寸与目标尺寸的比值大的那条边；
     * “短边”同理。
     * 如原图400 * 200，缩放为800 * 100，（400/800=0.5，200/100=2，0.5 < 2），所以在这个缩放中200那条是长边，400是短边。
     * <p/>
     * 默认 长边优先  0e
     * <p/>
     * 长边优先     0e
     * 短边优先     1e
     * 强制缩略     2e
     *
     * @param priority 缩放策略 0:长边优先  1:短边优先  2:强制缩略
     */
    public ALiImageURL zoomPriority(int priority) {
        if (priority < 0 || priority > 2) {
            throw new IllegalArgumentException("priority must in [0 , 2].");
        }
        this.e = priority + "e";
        return this;
    }

    /**
     * 倍数百分比。 小于100，即是缩小，大于100即是放大。
     * [1,1000]
     *
     * @param p 缩放比例
     */
    public ALiImageURL percent(int p) {
        if (p < 1 || p > 1000) {
            throw new IllegalArgumentException("p must in [1 , 1000].");
        }
        this.p = p + "p";

        return this;
    }

    /**
     * 是否对图片进行裁剪
     *
     * @param crop 0 不裁剪  1 裁剪
     */
    public ALiImageURL crop(boolean crop) {
        this.c = (crop ? 1 : 0) + "c";

        return this;
    }

    /**
     * 居中剪裁的图片，返回尺寸由w和h来订
     */
    public ALiImageURL centerCrop() {
        crop(true);
        zoomPriority(1);
        return this;
    }

    /**
     * 居中剪裁的图片，返回尺寸由w和h来订
     */
    public ALiImageURL centerCrop(int w, int h) {
        resize(w, h);
        centerCrop();
        return this;
    }

    /**
     * 按照长边缩放
     */
    public ALiImageURL centerInside() {
        crop(true);
        zoomPriority(0);
        return this;
    }

    /**
     * 按照长边缩放
     */
    public ALiImageURL centerInside(int w, int h) {
        resize(w, h);
        centerInside();
        return this;
    }

    /**
     * 参数的类型：x-y-width-length
     * 如100-50-200-150a 表示从点(100, 50) 裁剪大小为(200, 150)的图片。
     * 注意：可以将第三个参数，第四个参数置为0, 表示裁剪到图片的边缘。
     * 如100-50-0-0a 表示从点(100, 50) 裁剪到图片的最后
     *
     * @param x 起始点x坐标
     * @param y 起始点y坐标
     * @param w 要裁剪的宽度
     * @param h 要裁剪的高度
     */
    public ALiImageURL cropAdvanced(int x, int y, int w, int h) {
        advancedCrop = x + "-" + y + "-" + w + "-" + h + "a";
        return this;
    }

    /**
     * 从x,y位置一直裁剪到图片末端
     * x-y-0-0a
     *
     * @param x 起始点x坐标
     * @param y 起始点y坐标
     */
    public ALiImageURL cropAdvanced(int x, int y) {
        cropAdvanced(x, y, 0, 0);
        return this;
    }

    /**
     * 用户可以指定对某一个区域进行裁剪。在这里把图片分成９个区域。
     * 参数格式：<width>x<height>-<pos>rc.jpg
     * width 指的是裁剪的宽度[0, 4096]
     * Height 指的是裁剪的高度[0, 4096]
     * pos指的是裁剪区域,取值范围是[1,9],默认是左上角,区域数值对应表见下图
     * 如果想裁剪左上角，宽度是100, 高度是200的区域，参数是：100x200-1rc
     * 如果想裁剪左上角，宽度是100,高度是图片的原高度
     * 参数是：100x0-1rc 或者100x-1rc
     * 如果高度或者宽度不填，或者参数是0，或者参数大于原图。默认是按原图的高度或宽度返回。
     *
     * @param range 1左上 2中上 3右上 4左中 5中中 6右中 7左下 8中下 9右下
     * @param w     裁剪宽度 [0,4096]
     * @param h     裁剪高度 [0,4096]
     */
    public ALiImageURL cropRange(Range range, int w, int h) {
        if (w < 0 || w > 4096) {
            throw new IllegalArgumentException("w must in [0 , 4096].");
        }
        if (h < 0 || h > 4096) {
            throw new IllegalArgumentException("h must in [0 , 4096].");
        }
        this.cropRange = w + "x" + h + "-" + range.getIndex() + "rc";
        return this;
    }

    /**
     * 从图片取出圆形区域
     * 参数格式：[radius]-[type]ci
     *
     * @param radius [1, 4096] 如果radius能指定圆的半径。 但是圆的的半径不能超过原图的最小边的一半。如果半径超过。圆的大小仍然是原圆的最大内切圆。
     * @param type   [0, 1] 0:表示图片最终大小仍然是原图大小 1: 表示图片最终大小是能包含这个圆的最小正方形
     */
    public ALiImageURL circle(int radius, int type) {
        if (radius < 1 || radius > 4096) {
            throw new IllegalArgumentException("radius must in [1 , 4096].");
        }
        if (type < 0 || type > 1) {
            throw new IllegalArgumentException("type must in [0 , 1].");
        }

        this.circle = radius + "-" + type + "ci";
        return this;
    }

    /**
     * 自动切内圆，半径按照设置的宽度
     */
    public ALiImageURL circle() {
        autoCircle = true;
        return this;
    }

    /**
     * 从图片取出圆形区域
     * 参数格式：[radius]-2ci
     *
     * @param radius [1, 4096] radius指定圆角的半径。但是生成的最大圆角的的半径不能超过原图的最小边的一半。
     */
    public ALiImageURL rounded(int radius) {
        if (radius < 1 || radius > 4096) {
            throw new IllegalArgumentException("radius must in [1 , 4096].");
        }

        this.rounded = radius + "-2ci";
        return this;
    }

    /**
     * 索引切割
     * 将图片分成x，y轴按指定块大小，然后指定的索引，取出指定的区域。
     * 参数格式：[length]x-[index]ic 或[length]y-[index]ic
     *
     * @param type   0:x轴   1:y轴
     * @param length 切割长度 [1,切割边边长]，单位px。如果超出切割边的大小，返回原图
     * @param index  是表示块数。（0表示第一块）[0,最大块数)。如果超出最大块数，返回原图。
     */
    public ALiImageURL cropIndex(int type, int length, int index) {
        if (type < 0 || type > 1) {
            throw new IllegalArgumentException("type must in [0 , 1].");
        }

        this.cropIndex = length + (type == 0 ? "x" : "y") + "-" + index + "ic";
        return this;
    }


    /**
     * 参数格式：100w_100h_4e_100-0-0bgc
     * bgc 为 <red>-<green>-<blue>bgc
     *
     * @param w     指定目标缩略图的宽度 [1,4096]
     * @param h     指定目标缩略图的高度 [1,4096]
     * @param color 填充色值
     */
    public ALiImageURL zoomFillColor(int w, int h, int color) {
        resize(w, h);
        zoomFillColor(color);
        return this;
    }

    /**
     * 如果前面已经调用了设置高度和宽度的方法，直接调用这个方法
     * 参数格式：100w_100h_4e_100-0-0bgc
     * bgc 为 <red>-<green>-<blue>bgc
     *
     * @param color 填充色值
     */
    public ALiImageURL zoomFillColor(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        //注意必须有4e这个参数

        fitColor = "4e_" + red + "-" + green + "-" + blue + "bgc";
        return this;
    }

    /**
     * 将原图保存成jpg格式，如果原图是png,webp, bmp存在透明通道，默认会把透明填充成黑色。如果想把透明填充成白色可以指定1wh参数
     */
    public ALiImageURL jpg() {
        this.format = ".jpg";
        return this;
    }

    /**
     * @1pr.jpg
     */
    public ALiImageURL progressJpg() {
        this.format = "1pr.jpg";
        return this;
    }

    /**
     * 将原图保存成png格式
     */
    public ALiImageURL png() {
        this.format = ".png";
        return this;
    }

    /**
     * android 默认返回webp，如果调用noWebp则返回原图格式
     */
    public ALiImageURL noWebp() {
        src();
        return this;
    }

    /**
     * 将原图保存成webp格式
     */
    public ALiImageURL webp() {
        this.format = ".wep";
        return this;
    }

    /**
     * 将原图保存成bmp格式
     */
    public ALiImageURL bmp() {
        this.format = ".bmp";
        return this;
    }

    /**
     * 按原图格式返回，如果原图是gif, 此时返回gif格式第一帧,保存成jpg格式，而非gif格式
     */
    public ALiImageURL src() {
        jpg();
        return this;
    }

    /**
     * 可以对处理后的图片进行按顺时针旋转
     * 90r
     *
     * @param angle [0, 360] 默认值 ：0(表示不旋转)
     */
    public ALiImageURL rotate(int angle) {

        if (angle < 0 || angle > 360) {
            throw new IllegalArgumentException("type must in [0 , 360].");
        }
        this.angle = angle + "r";
        return this;
    }

    /**
     * 根据exif里信息进行旋转
     * 参数形式 0o,1o,2o
     * 进行自动旋转
     * 0：表示按原图默认方向，不进行自动旋转
     * 1：表示根据图片的旋转参数，对图片进行自动旋转，如果存在缩略参数，是先进行缩略，再进行旋转。
     * 2: 表示根据图片的旋转参数，对图片进行自动旋转，如果存在缩略参数，先进行旋转，再进行缩略
     * <p/>
     * 本函数直接使用  2o
     */
    public ALiImageURL autoRotate(int type) {
        autoRotate = type + "o";
        return this;
    }

    /**
     * 锐化
     * 可以对处理后的图片进行锐化处理，使图片变得清晰。
     *
     * @param value [50, 399]  表示进行锐化处理。取值为锐化参数，参数越大，越清晰。为达到较优效果，推荐取值为100
     */
    public ALiImageURL sharpen(int value) {
        if (value < 50 || value > 399) {
            throw new IllegalArgumentException("value must in [50 , 399].");
        }
        this.sh = value + "sh";
        return this;
    }

    /**
     * 模糊效果
     * 可以对处理后的图片进行锐化处理，使图片变得清晰。
     * 如：3-2bl 模糊半径是3,标准差是2
     *
     * @param radius 模糊半径 取值在 [1,50]， radius越大，越模糊
     * @param sigma  正态分布的标准差 取值 [1,50]，越大，越模糊
     */
    public ALiImageURL blur(int radius, int sigma) {
        if (radius < 1 || radius > 50) {
            throw new IllegalArgumentException("radius must in [1 , 50].");
        }
        if (sigma < 1 || sigma > 50) {
            throw new IllegalArgumentException("sigma must in [1 , 50].");
        }
        this.blur = radius + "-" + sigma + "bl";
        return this;
    }

    /**
     * 亮度调整
     * 0表示原图亮度，小于0表示亮度越低，大于0表示亮度越高
     *
     * @param value [-100, 100]
     */
    public ALiImageURL brightness(int value) {
        if (value < -100 || value > 100) {
            throw new IllegalArgumentException("value must in [-100 , 100].");
        }

        this.brightness = value + "b";

        return this;
    }

    /**
     * 对比度调整
     * 0表示原图对比度，小于0表示对比越低，大于0表示对比越高
     *
     * @param value [-100, 100]
     */
    public ALiImageURL contrast(int value) {
        if (value < -100 || value > 100) {
            throw new IllegalArgumentException("value must in [-100 , 100].");
        }

        this.contrast = value + "d";
        return this;
    }

    /**
     * 获取文件exif信息以及图片info信息
     * infoexif 标签
     * <p/>
     * 如果有exif
     * {
     * "DateTime": {"value": "2015:02:11 15:38:27"},
     * "FileSize": {"value": "23471"},
     * "Format": {"value": "jpg"},
     * "GPSLatitude": {"value": "0deg "},
     * "GPSLatitudeRef": {"value": "North"},
     * "GPSLongitude": {"value": "0deg "},
     * "GPSLongitudeRef": {"value": "East"},
     * "ImageHeight": {"value": "333"},
     * "ImageWidth": {"value": "424"},
     * "Orientation": {"value": "7"}
     * }
     * <p/>
     * 如果没有exif
     * {
     * "FileSize": {"value": "21839"},
     * "Format": {"value": "jpg"},
     * "ImageHeight": {"value": "267"},
     * "ImageWidth": {"value": "400"}
     * }
     */
    public ALiImageURL infoexif() {
        this.infoexif = "infoexif";
        return this;
    }

    /**
     * 样式
     * !pipe1
     *
     * @param name 样式的名称
     */
    public ALiImageURL setStyle(String name) {
        //@!name
        this.style = "!" + name;
        return this;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        result.append(url);
        result.append("@");
        result.append(getParams(w));
        result.append(getParams(h));
        result.append(getParams(l));
        result.append(getParams(Q));
        result.append(getParams(e));
        result.append(getParams(p));
        result.append(getParams(c));
        result.append(getParams(cropRange));
        result.append(getParams(circle));
        result.append(getParams(rounded));
        result.append(getParams(cropIndex));
        result.append(getParams(fitColor));
        result.append(getParams(angle));
        result.append(getParams(autoRotate));
        result.append(getParams(sh));
        result.append(getParams(blur));
        result.append(getParams(brightness));
        result.append(getParams(contrast));
        result.append(getParams(infoexif));
        result.append(getParams(style));

        if (result.lastIndexOf("_") == result.length() - 1) {
            result.deleteCharAt(result.length() - 1);
        }

        if (!TextUtils.isEmpty(advancedCrop)) {
            result.append("|");
            result.append(advancedCrop);
        }

        if (result.lastIndexOf("_") == result.length() - 1) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append(format);
//        if (BuildConfig.DEBUG) {
            Log.d("ali", "image url = " + result.toString());
//        }
        return result.toString();
    }

    private String getParams(String value) {
        return TextUtils.isEmpty(value) ? "" : (value + "_");
    }
}
