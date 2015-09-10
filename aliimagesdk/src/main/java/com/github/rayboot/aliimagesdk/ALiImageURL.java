package com.github.rayboot.aliimagesdk;

import android.graphics.Color;

/**
 * author: rayboot  Created on 15/9/10.
 * email : sy0725work@gmail.com
 */
public class ALiImageURL {

    public enum Range {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        CENTER_LEFT,
        CENTER_CENTER,
        CENTER_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
    }

    /**
     * 缩略
     * hh
     *
     * @param h 指定高度
     */
    public void height(int h) {
        if (h > 4096) {
            throw new IllegalArgumentException("h must in [1 , 4096].");
        }

    }

    /**
     * 缩略
     * ww
     *
     * @param w 指定宽度
     */
    public void width(int w) {
        if (w > 4096) {
            throw new IllegalArgumentException("w must in [1 , 4096].");
        }

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
     * @param q [1,100]
     */
    public void quality(int q) {
        if (q < 1 || q > 100) {
            throw new IllegalArgumentException("q must in [1 , 100]");
        }

    }

    /**
     * 缩略
     * ww_hh
     *
     * @param w 指定目标缩略图的宽度 [1,4096]
     * @param h 指定目标缩略图的高度 [1,4096]
     */
    public void resize(int w, int h) {
        height(h);
        width(w);
    }

    /**
     * 目标缩略图大于原图是否处理
     * 1l  or  0l
     *
     * @param deal 值是1, 即不处理，是0，表示处理. 默认0-处理
     */
    public void biggerOpt(boolean deal) {
        if (deal) {

        }
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
    public void zoomPriority(int priority) {

    }

    /**
     * 倍数百分比。 小于100，即是缩小，大于100即是放大。
     * [1,1000]
     *
     * @param p 缩放比例
     */
    public void percent(int p) {

    }

    /**
     * 是否对图片进行裁剪
     *
     * @param crop 0 不裁剪  1 裁剪
     */
    public void crop(boolean crop) {

    }

    /**
     * 居中剪裁的图片，返回尺寸由w和h来订
     */
    public void centerCrop() {
        crop(true);
        zoomPriority(1);

    }

    /**
     * 居中剪裁的图片，返回尺寸由w和h来订
     */
    public void centerCrop(int w, int h) {
        resize(w, h);
        centerCrop();

    }

    /**
     * 按照长边缩放
     */
    public void centerInside() {
        zoomPriority(0);
    }

    /**
     * 按照长边缩放
     */
    public void centerInside(int w, int h) {
        resize(w, h);
        centerInside();
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
    public void cropAdvanced(int x, int y, int w, int h) {

    }

    /**
     * 从x,y位置一直裁剪到图片末端
     * x-y-0-0a
     *
     * @param x 起始点x坐标
     * @param y 起始点y坐标
     */
    public void cropAdvanced(int x, int y) {
        cropAdvanced(x, y, 0, 0);
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
    public void cropRange(Range range, int w, int h) {

    }

    /**
     * 从图片取出圆形区域
     * 参数格式：[radius]-[type]ci
     *
     * @param radius [1, 4096] 如果radius能指定圆的半径。 但是圆的的半径不能超过原图的最小边的一半。如果半径超过。圆的大小仍然是原圆的最大内切圆。
     * @param type   [0, 1] 0:表示图片最终大小仍然是原图大小 1: 表示图片最终大小是能包含这个圆的最小正方形
     */
    public void circle(int radius, int type) {

    }

    /**
     * 自动切内圆，半径按照设置的宽度
     */
    public void circle() {

    }

    /**
     * 从图片取出圆形区域
     * 参数格式：[radius]-2ci
     *
     * @param radius [1, 4096] radius指定圆角的半径。但是生成的最大圆角的的半径不能超过原图的最小边的一半。
     */
    public void rounded(int radius) {

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
    public void cropIndex(int type, int length, int index) {

    }


    /**
     * 参数格式：100w_100h_4e_100-0-0bgc
     * bgc 为 <red>-<green>-<blue>bgc
     *
     * @param w     指定目标缩略图的宽度 [1,4096]
     * @param h     指定目标缩略图的高度 [1,4096]
     * @param color 填充色值
     */
    public void zoomFillColor(int w, int h, int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        //注意必须有4e这个参数

    }

    /**
     * 如果前面已经调用了设置高度和宽度的方法，直接调用这个方法
     * 参数格式：100w_100h_4e_100-0-0bgc
     * bgc 为 <red>-<green>-<blue>bgc
     *
     * @param color 填充色值
     */
    public void zoomFillColor(int color) {

    }

    /**
     * 将原图保存成jpg格式，如果原图是png,webp, bmp存在透明通道，默认会把透明填充成黑色。如果想把透明填充成白色可以指定1wh参数
     */
    public void jpg() {

    }

    /**
     * @1pr.jpg
     */
    public void progressJpg() {

    }

    /**
     * 将原图保存成png格式
     */
    public void png() {

    }

    /**
     * android 默认返回webp，如果调用noWebp则返回原图格式
     */
    public void noWebp() {

    }

    /**
     * 将原图保存成webp格式
     */
    public void webp() {

    }

    /**
     * 将原图保存成bmp格式
     */
    public void bmp() {

    }

    /**
     * 按原图格式返回，如果原图是gif, 此时返回gif格式第一帧,保存成jpg格式，而非gif格式
     */
    public void src() {

    }

    /**
     * 可以对处理后的图片进行按顺时针旋转
     *
     * @param angle [0, 360] 默认值 ：0(表示不旋转)
     */
    public void rotate(int angle) {

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
    public void autoRotate() {

    }

    /**
     * 锐化
     * 可以对处理后的图片进行锐化处理，使图片变得清晰。
     *
     * @param value [50, 399]  表示进行锐化处理。取值为锐化参数，参数越大，越清晰。为达到较优效果，推荐取值为100
     */
    public void sharpen(int value) {

    }

    /**
     * 模糊效果
     * 可以对处理后的图片进行锐化处理，使图片变得清晰。
     * 如：3-2bl 模糊半径是3,标准差是2
     *
     * @param radius 模糊半径 取值在 [1,50]， radius越大，越模糊
     * @param sigma  正态分布的标准差 取值 [1,50]，越大，越模糊
     */
    public void blur(int radius, int sigma) {

    }

    /**
     * 亮度调整
     * 0表示原图亮度，小于0表示亮度越低，大于0表示亮度越高
     *
     * @param value [-100, 100]
     */
    public void brightness(int value) {

    }

    /**
     * 对比度调整
     * 0表示原图对比度，小于0表示对比越低，大于0表示对比越高
     *
     * @param value [-100, 100]
     */
    public void contrast(int value) {

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
    public void infoexif() {

    }

    /**
     * 样式
     * !pipe1
     * @param name 样式的名称
     */
    public void setStyle(String name) {
        //@!name
    }
}
