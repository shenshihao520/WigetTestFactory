package com.example.shenshihao520.wigettestfactory.java_class_test.proxy_dynamic;

/**
 * Created by shenshihao520 on 2017/8/28.
 */


/**
 * 外界环境，一般都是通过经纪人来接触明星
 */
public class Environment {

    public static void main(String[] args) {
        Star huangBo = new Star("HuangBo");
        ProxyHandler proxyHandler = new ProxyHandler(huangBo);
        IMovieStar agent = (IMovieStar) proxyHandler.getProxy();
        agent.movieShow(1000000000);
        agent.tvShow(100);

        //黄渤早年其实是个歌手！唱歌不得志只好去演戏，成为影帝后人们才关注他的歌声，真是个“看脸、看名”的世界
        ISingerStar singerAgent = (ISingerStar) proxyHandler.getProxy();
        singerAgent.sing(1024);
    }
}