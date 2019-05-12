package com.bobo.shoppingmall.home.bean;

import java.util.List;

/**
 * Created by 求知自学网 on 2019/5/12. Copyright © Leon. All rights reserved.
 * Functions: 首页数据对象模型 - 本项目使用的是阿里开源的框架fastjson解析的。
 *  注意： gsonFormat  生成的是 fastjson 数据！
 */
public class ResultBeanData {


    /**
     * code : 200
     * msg : 请求成功
     * result : {"act_info":[{"icon_url":"/operation/img/1478169868/1478761370286.png","name":"尚硅
     */

    private int code;
    private String msg;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * act_info : [{"icon_url":"/operation/img/1478169868/1478761370286.png","name":"尚硅谷福利专
         * banner_info : [{"image":"/1478770583834.png","option":3,"type":0,"value":{"url":"/act2016
         * channel_info : [{"channel_name":"服饰","image":"/app/img/menu-cyc.png","option":2,"type"
         * hot_info : [{"cover_price":"159.00","figure":"/1477984921265.jpg","name":"现货【一方尘寰】
         * recommend_info : [{"cover_price":"138.00","figure":"/supplier/1478873740576.jpg","name":
         * seckill_info : {"end_time":"1479052800","list":[{"cover_price":"20.00","figure":"/147848
         */

        private SeckillInfoBean seckill_info;
        private List<ActInfoBean> act_info;
        private List<BannerInfoBean> banner_info;
        private List<ChannelInfoBean> channel_info;
        private List<HotInfoBean> hot_info;
        private List<RecommendInfoBean> recommend_info;

        public SeckillInfoBean getSeckill_info() {
            return seckill_info;
        }

        public void setSeckill_info(SeckillInfoBean seckill_info) {
            this.seckill_info = seckill_info;
        }

        public List<ActInfoBean> getAct_info() {
            return act_info;
        }

        public void setAct_info(List<ActInfoBean> act_info) {
            this.act_info = act_info;
        }

        public List<BannerInfoBean> getBanner_info() {
            return banner_info;
        }

        public void setBanner_info(List<BannerInfoBean> banner_info) {
            this.banner_info = banner_info;
        }

        public List<ChannelInfoBean> getChannel_info() {
            return channel_info;
        }

        public void setChannel_info(List<ChannelInfoBean> channel_info) {
            this.channel_info = channel_info;
        }

        public List<HotInfoBean> getHot_info() {
            return hot_info;
        }

        public void setHot_info(List<HotInfoBean> hot_info) {
            this.hot_info = hot_info;
        }

        public List<RecommendInfoBean> getRecommend_info() {
            return recommend_info;
        }

        public void setRecommend_info(List<RecommendInfoBean> recommend_info) {
            this.recommend_info = recommend_info;
        }

        public static class SeckillInfoBean {
            /**
             * end_time : 1479052800
             * list : [{"cover_price":"20.00","figure":"/1478489000522.png","name":"尚硅谷购物节特供
             * start_time : 1478772000
             */

            private String end_time;
            private String start_time;
            private List<ListBean> list;

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * cover_price : 20.00
                 * figure : /1478489000522.png
                 * name : 尚硅谷购物节特供优惠券  满600-120优惠券
                 * origin_price : 20.00
                 * product_id : 7100
                 */

                private String cover_price;
                private String figure;
                private String name;
                private String origin_price;
                private String product_id;

                public String getCover_price() {
                    return cover_price;
                }

                public void setCover_price(String cover_price) {
                    this.cover_price = cover_price;
                }

                public String getFigure() {
                    return figure;
                }

                public void setFigure(String figure) {
                    this.figure = figure;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getOrigin_price() {
                    return origin_price;
                }

                public void setOrigin_price(String origin_price) {
                    this.origin_price = origin_price;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }
            }
        }

        public static class ActInfoBean {
            /**
             * icon_url : /operation/img/1478169868/1478761370286.png
             * name : 尚硅谷福利专区之111.1专区
             * url : /oper/1478169868app.html
             */

            private String icon_url;
            private String name;
            private String url;

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class BannerInfoBean {
            /**
             * image : /1478770583834.png
             * option : 3
             * type : 0
             * value : {"url":"/act20161111?cyc_app=1"}
             */

            private String image;
            private int option;
            private int type;
            private ValueBean value;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public ValueBean getValue() {
                return value;
            }

            public void setValue(ValueBean value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * url : /act20161111?cyc_app=1
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class ChannelInfoBean {
            /**
             * channel_name : 服饰
             * image : /app/img/menu-cyc.png
             * option : 2
             * type : 1
             * value : {"channel_id":"8"}
             */

            private String channel_name;
            private String image;
            private int option;
            private int type;
            private ValueBeanX value;

            public String getChannel_name() {
                return channel_name;
            }

            public void setChannel_name(String channel_name) {
                this.channel_name = channel_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public ValueBeanX getValue() {
                return value;
            }

            public void setValue(ValueBeanX value) {
                this.value = value;
            }

            public static class ValueBeanX {
                /**
                 * channel_id : 8
                 */

                private String channel_id;

                public String getChannel_id() {
                    return channel_id;
                }

                public void setChannel_id(String channel_id) {
                    this.channel_id = channel_id;
                }
            }
        }

        public static class HotInfoBean {
            /**
             * cover_price : 159.00
             * figure : /1477984921265.jpg
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * product_id : 9356
             */

            private String cover_price;
            private String figure;
            private String name;
            private String product_id;

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }
        }

        public static class RecommendInfoBean {
            /**
             * cover_price : 138.00
             * figure : /supplier/1478873740576.jpg
             * name : 【尚硅谷】日常 萌系小天使卫衣--白色款
             * product_id : 10659
             */

            private String cover_price;
            private String figure;
            private String name;
            private String product_id;

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }
        }
    }
}
