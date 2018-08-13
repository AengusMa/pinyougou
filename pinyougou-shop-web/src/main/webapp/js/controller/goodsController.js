//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, uploadService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    };

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };
    //添加
    $scope.add = function () {
        //富文本编辑器赋值
        $scope.entity.goodsDesc.introduction = editor.html();
        goodsService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    alert("新增成功");
                    $scope.entity = {};
                    editor.html("");
                } else {
                    alert(response.message);
                }
            }
        );
    };
    $scope.image_entity = {};
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(
            function (response) {
                if (response.success) {
                    $scope.image_entity.url = response.message;
                } else {
                    alert(response.message);
                }
            }
        )
    };
    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};
    $scope.addImageEntity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };
    //移除图片
    $scope.removeImageEntity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };
    //查询以及商品分类列表
    $scope.selectItemCat1List = function () {
        itemCatService.getByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        )
    };
    //entity.goods.category1Id变量改变时触发事件
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.getByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
                $scope.itemCat3List = {};
                $scope.typeTemplate.brandIds = {};
                $scope.entity.goods.typeTemplateId = "";
                $scope.entity.goodsDesc.customAttributeItems = {};
            }
        )
    });
    //entity.goods.category2Id变量改变时触发事件
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.getByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
                $scope.typeTemplate.brandIds = {};
                $scope.entity.goods.typeTemplateId = "";
                $scope.entity.goodsDesc.customAttributeItems = {};
            }
        )
    });
    //读取模板id
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
            }
        )
    });

    //读取模板id后，读取品牌列表,
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.getById(newValue).success(
            function (response) {
                $scope.typeTemplate = response;
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
                //扩展属性
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);

            }
        );
        typeTemplateService.getSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        )
    });

    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};
    $scope.updateAttribute = function ($event, name, value) {
        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,
            'attributeName', name);
        //集合中不为空
        if (object != null) {
            //选中状态
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {
                //移除选项
                object.attributeValue.splice(object.attributeValue.indexOf(value), 1);
                //选项都取消，移除此条记录
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object), 1
                    );
                }
            }

        } else {
            $scope.entity.goodsDesc.specificationItems.push({
                "attributeName": name,
                "attributeValue": [value]
            });
        }
    };
    //创建SKU列表
    $scope.createItemList = function () {
        //列表初始化
        $scope.entity.itemList = [{spec: {}, price: 0, num: 99999, status: '0', isDefault: '0'}]
        var items = $scope.entity.goodsDesc.specificationItems;
        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList,
                items[i].attributeName, items[i].attributeValue);
        }


    };
    addColumn = function (list, columnName, columnValues) {
        var newList = [];
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (j = 0; j < columnValues.length; j++) {
                //深克隆
                var newRow = JSON.parse(JSON.stringify(oldRow));
                newRow.spec[columnName] = columnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

});
