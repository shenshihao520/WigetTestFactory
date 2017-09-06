package com.example.shenshihao520.wigettestfactory.java_class_test.proxy_dynamic;

/**
 * Created by shenshihao520 on 2017/8/28.
 */

/**
 * 明星，可能是影歌双栖
 */
public class Star implements IMovieStar, ISingerStar {
    private String mName;

    public Star(String name) {
        mName = name;
    }

    @Override
    public void movieShow(int money) {
        System.out.println(mName + " 出演了部片酬 " + money + " 元的电影");
    }

    @Override
    public void tvShow(int money) {
        System.out.println(mName + " 出演了部片酬 " + money + " 元的电视剧");
    }

    /**
     * 黄渤早年其实是个歌手！唱歌一流
     * @param number 歌曲数
     */
    @Override
    public void sing(int number) {
        System.out.println(mName + " 唱了 " + number + " 首歌");
    }
}