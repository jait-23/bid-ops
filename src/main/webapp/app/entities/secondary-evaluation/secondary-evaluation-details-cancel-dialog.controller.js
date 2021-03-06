'use strict';

angular.module('bidopscoreApp').controller(
		'SecondaryEvaluationDetailsCancelDialogController',
		[
			'$state',
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'SecondaryEvaluation',
		function($state, $scope, $stateParams, $uibModalInstance, SecondaryEvaluation) {

			var vm = this;

			$scope.clear = function() {
				$uibModalInstance.dismiss('cancel');
			};
			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
				$state.go('secondary-evaluation');
			};
		}]);
