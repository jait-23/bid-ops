'use strict';

angular.module('bidopscoreApp')
		.controller(
				'SolicitationsExtraInfoCreateDialogController',
				[
						'$scope',
						'$stateParams',
						'$uibModalInstance',
						'Solicitations',
				function($scope, $stateParams, $uibModalInstance, Solicitations) {

					$scope.attribute = null;
					$scope.value = null;
					$scope.saveConfirmed = function() {
						$scope.saveExtraInfo($scope.attribute, $scope.value);
						$uibModalInstance.close();
					};
					
					$scope.clear = function() {
						$uibModalInstance.dismiss('cancel');
					};

				}]);