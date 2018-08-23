app.controller('searchController', function ($scope, searchService) {
    //定义搜索对象属性
    $scope.searchMap = {'keywords': '', 'category': '', 'brand': '', 'spec': {}};
    //搜索
    $scope.search = function () {
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
            }
        );
    };
    $scope.addSearchItem = function (key, value) {
        //点击的品牌或分类
        if (key == 'category' || key == 'brand') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();
    };
    $scope.removeSearchItem = function (key) {
        //点击的品牌或分类
        if (key == 'category' || key == 'brand') {
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key];
        }
    }
});