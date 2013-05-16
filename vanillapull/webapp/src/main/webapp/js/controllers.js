'use strict';

/* Controllers */

function StockCtrl($scope, $http) {
    $http.get('services/instrument/list').success(function(data) {
        $scope.stocks = data;
    });
}