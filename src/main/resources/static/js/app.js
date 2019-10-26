var app = angular.module('MyApp', ['ui.router']);
app.config(function ($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/');

	$stateProvider.state("operations", {
		url: "/operations",
		templateUrl: "views/operations.html",
		controller: "OperationsController"
	}
	);
	$stateProvider.state("comptes", {
		url: "/comptes",
		templateUrl: "views/comptes.html",
		controller: "ComptesController"
	}
	)
});

app.controller("OperationsController", function ($scope, $http) {
});
app.controller("ComptesController", function ($scope, $http) {
	$scope.listOperations = [];
	$scope.codeCompte = "";
	$scope.pageCourante = 0;
	$scope.size = 3;
	$scope.pages = [];
	$scope.chercher = function () {
		$http.get("http://localhost:8089/listOperations?codeCompte=" + $scope.codeCompte + "&page=" + $scope.pageCourante + "&size=" + $scope.size)
			.success(
				function (data) {
					$scope.listOperations = data;
					$scope.pages = new Array(data.totalPages);
				})
			.error(function (err) {
				Console.log(err);
			});
	}

	$scope.gotoPage = function (p) {
		$scope.pageCourante = p;
		$scope.chercher();
	}
});