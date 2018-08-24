app.controller('searchController', function ($scope, searchService) {
    //定义搜索对象属性
    $scope.searchMap = {'keywords': '', 'category': '', 'brand': '', 'spec': {},'price':'',
                        "pageNo":1,"pageSize":40};
    //搜索
    $scope.search = function () {
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo );
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
                //查询后显示第一页
                // $scope.searchMap.pageNo = 1;
                buildPageLable();

            }
        );
    };
    buildPageLable=function(){
        $scope.pageLable=[];
        var firstPage = 1;
        var lastPage = $scope.resultMap.totalPages;
        $scope.firstDot=true;
        $scope.lastDot= true;

        if($scope.resultMap.totalPages>5){
            //当前页码小于3
            if($scope.searchMap.pageNo<=3 ){
                lastPage=5;
                $scope.firstDot = false;
            //显示后五页
            }else if($scope.searchMap.pageNo>=$scope.resultMap.totalPages-2){
                firstPage = $scope.resultMap.totalPages-4;
                $scope.lastDot= false;
            }else{
                firstPage = $scope.searchMap.pageNo-2;
                lastPage = $scope.searchMap.pageNo+2;
            }
        }else{
            $scope.firstDot=false;
            $scope.lastDot= false;
        }

        for(var i=firstPage;i<=lastPage;i++){
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
    $scope.queryByPage = function (pageNo) {
        if(pageNo<1 ||pageNo >$scope.resultMap.totalPages){
            return;
        }
        $scope.searchMap.pageNo = pageNo;
        $scope.search();
    };
    $scope.isTopPage =function () {
        if($scope.searchMap.pageNo==1){
            return true;
        }else{
            return false;
        }
    };
    $scope.isEndPage =function () {
        if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
            return true;
        }else{
            return false;
        }
    };
});