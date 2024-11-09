module.exports = {
    chainWebpack: config =>{
        config.plugin('html')
            .tap(args => {
                args[0].title = "通用后台管理系统";
                args[0].keywords = "通用后台管理系统";
                args[0].description = "通用后台管理系统";
                return args;
            })
    },
};
