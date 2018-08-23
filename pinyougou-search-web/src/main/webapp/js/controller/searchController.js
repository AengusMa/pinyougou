app.controller('searchController', function ($scope, searchService) {
    //定义搜索对象属性
    $scope.searchMap = {'keywords': '', 'category': '', 'brand': '', 'spec': {},'price':'',
                        "pageNo":1,"pageSize":40};
    //搜索
    $scope.search = function () {
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;

                buildPageLable();

            }
        );
    };
    buildPageLable=function(){
        $scope.pageLable=[];
        for(var i=1;i<=$scope.resultMap.totalPages;i++){
            $scope.pageLable.push(i)
        }
    };
    $scope.addSearchItem = function (key, value) {
        //点击的品牌或分类
        if (key == 'category' || key == 'brand' || key=='price') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();
    };
    $scope.removeSearchItem = function (key) {
        //点击的品牌或分类
        if (key == 'category' || key == 'brand' || key=='price') {
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key];
        }
    };

});