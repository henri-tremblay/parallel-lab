'use strict';

/* Controllers */

function StockCtrl($scope, $http) {
    $http.get('services/instrument/list').success(function(data) {
        $scope.stocks = data;
    });
    $scope.price = 'Unknown';

    $scope.change = function () {
        if($scope.symbol == undefined || $scope.strike == undefined  || $scope.maturity  == undefined ) {
            $scope.price = 'Unknown';
            return;
        }

        var url = 'services/pricer/price?symbol=' + $scope.symbol + '&maturity=' + $scope.maturity + '&strike=' + $scope.strike;
        $http.get(url).
            success(function (data) {
                $scope.price = data;
            });
    };
}