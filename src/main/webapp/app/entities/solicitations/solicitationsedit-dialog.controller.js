'use strict';

angular
		.module('bidopscoreApp')
		.controller(
				'SolicitationsEditDialogController',
				[
						'$scope',
						'$stateParams',
						'$modalInstance',
						'Solicitations',
						function($scope, $stateParams, $modalInstance, entity,
								Solicitations) {

							$scope.isAttributeKeyEditable = function() {
								return $scope.editKey !== 'Platform'
										&& $scope.editKey !== 'platform'
										&& $scope.editKey !== 'Type'
										&& $scope.editKey !== 'type';
							};

							$scope.key = $scope.editKey;
							$scope.value = $scope.solicitations.requiredDocuments[$scope.editKey];

							$scope.saveAttribute = function() {
								$scope.solicitations.requiredDocuments[$scope.key] = $scope.value;
								if ($scope.key !== $scope.editKey) {
									delete $scope.solicitations.requiredDocuments[$scope.editKey];
								}
								$scope.save();
							}
						} ]);
