'use strict';

angular.module('bidopscoreApp').controller(
		'ApproveDialogSaveController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'PrimaryEvaluations',
		function($scope, $stateParams, $uibModalInstance, PrimaryEvaluations) {

			$scope.saveConfirmed = function() {
				$scope.save();
				$uibModalInstance.close();
			}
			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
		}]);