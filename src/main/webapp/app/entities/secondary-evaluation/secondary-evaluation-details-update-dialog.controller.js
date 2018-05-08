'use strict';

angular.module('bidopscoreApp').controller(
		'SecondaryEvaluationDialogDetailsUpdateController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'SecondaryEvaluation',
		function($scope, $stateParams, $uibModalInstance, SecondaryEvaluation) {

			$scope.saveConfirmed = function() {
				$scope.save();
				$uibModalInstance.close();
			}
			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
		}]);