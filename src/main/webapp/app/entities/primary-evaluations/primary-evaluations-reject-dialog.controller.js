'use strict';

angular.module('bidopscoreApp').controller(
		'RejectDialogSaveController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'PrimaryEvaluations',
		function($scope, $stateParams, $uibModalInstance, PrimaryEvaluations) {

			$scope.saveConfirmed = function() {
				$scope.saveReject();
				$uibModalInstance.close();
			}
			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
		}]);