//跳出iframe框架
$(function () {
    if (top.location != location){
        top.location.href = location.href;
    }
});
$(function () {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    var url = pathName.substring(webName.length)
    var ctx =  window.location.protocol + '//' + window.location.host + '/'+ webName;

    $.ajax({
        url:ctx+'/commonApi/getMenu.action',
        dataType:'JSON',
        type:'GET',
        success:function (data) {
            var menuList = data.menu;
            var mobileMenusParent = '';
            var mobileMenus = [];
            console.log(menuList)
            //获取菜单并找出手机导航菜单
            for (var i = 0; i < menuList.length; i++) {
                if (menuList[i].menuCode == "mobile") {
                    mobileMenusParent = menuList[i].menuId;
                    for (var j = 0; j < menuList.length; j++) {
                        if (menuList[j].parentId == mobileMenusParent) {
                            mobileMenus.push(menuList[j]);
                        }
                    }
                }

            }
            console.log(mobileMenus);

            
            //拼接导航菜单
            var navList = "<ul class='nav navbar-nav'>";
            for (var i = 0; i < mobileMenus.length; i++) {

                if (i == 0){
                    navList += "<li "+(mobileMenus[i].href == url?"class ='active'":"")+"><a href='"+ctx+mobileMenus[i].href+"'>"+mobileMenus[i].menuName+"<span class='sr-only'>(current)</span></a></li>"
                } else if (i == 1) {
                    navList += "<li "+(mobileMenus[i].href == url?"class ='active'":"")+"><a href='"+ctx+mobileMenus[i].href+"'>"+mobileMenus[i].menuName+"</a></li>"
                }else {
                    if (i == 2) {
                        navList +="<li class=\"dropdown\">\n" ;
                        navList += "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>其他 <span class='caret'></span></a>";
                        navList += "<ul class='dropdown-menu'>";
                    }
                    navList +="<li "+(mobileMenus[i].href == url?"class ='active'":"")+"><a href='"+ctx+mobileMenus[i].href+"'>"+mobileMenus[i].menuName+"</a></li>\n";


                }
            }
            navList += "</ul>";
            navList += "</li>";
            navList +="</ul>"
            console.log(navList);
            $("#nav-table").html(navList);
        },
        error:function () {

        }
    });
})

