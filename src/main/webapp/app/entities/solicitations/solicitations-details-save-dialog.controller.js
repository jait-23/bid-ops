'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsDialogDetailsSaveController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'Solicitations',
		function($scope, $stateParams, $uibModalInstance, Solicitations) {

			$scope.saveConfirmed = function() {
				$scope.save();
				$uibModalInstance.close();
			}
			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
		}]);