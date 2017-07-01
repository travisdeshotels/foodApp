var app = angular.module('foodApp', []);

app.controller("foodControl", function($scope, $http){
    $scope.restaurants = [];
    $scope.selectedRestaurant = {};

    $scope.getRestaurants = function() {
        $http.get("/api/food/restaurant").then(
            //success
            function(response) {
                $scope.restaurants = response.data;
            }
        );
    };

    $scope.selectRestaurant = function(choice){
        $scope.selectedRestaurant = choice;
        console.log(choice);
    }

    $scope.getRestaurants();
});