'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsExtraInfoEditDialogController', 
		[
						'$scope',
						'$stateParams',
						'$uibModalInstance',
						'Solicitations',
		function($scope, $stateParams, $uibModalInstance, Solicitations) {

			$scope.isAttributeKeyEditable = function() {
				return $scope.editKey !== 'Platform'
						&& $scope.editKey !== 'platform'
						&& $scope.editKey !== 'Type'
						&& $scope.editKey !== 'type';
			};
			
			$scope.key = $scope.editKey;
			$scope.value = $scope.solicitations[$scope.key];
			
			$scope.saveAttribute = function() {
				$scope.solicitations[$scope.key] = $scope.value;
				if ($scope.key !== $scope.editKey) {
					delete $scope.solicitations[$scope.editKey];
				}
				$scope.save();
				$uibModalInstance.close();
			}
			$scope.cancel = function(){
				$uibModalInstance.close();
			}
		}]);
