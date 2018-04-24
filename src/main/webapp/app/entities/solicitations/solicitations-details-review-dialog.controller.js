'use strict';

angular.module('bidopscoreApp').controller(
		'SolicitationsDialogDetailsReviewController',
		[
			'$scope',
			'$stateParams',
			'$uibModalInstance',
			'Solicitations',
			'SolicitationReviewer',
		function($scope, $stateParams, $uibModalInstance, Solicitations, SolicitationReviewer) {
				
				$scope.solicitationReviewer = SolicitationReviewer.query();
				// console.log(solicitationReviewer);
				
				$scope.saveConfirmed = function() {
					/*console.log($scope.solicitationId + $scope.reviewerId);
					$scope.solicitationReviewer.solicitationId = $scope.solicitationId;
					$scope.solicitationReviewer.reviewerId = $scope.reviewerId;
					
					if ($scope.solicitationReviewer.id !== null) {
						$scope.solicitationReviewer.solicitationId = $scope.solicitationId;
						$scope.solicitationReviewer.reviewerId = $scope.reviewerId;
		                SolicitationReviewer.update($scope.solicitationReviewer, onSaveSuccess, onSaveError);
		            } else {
		            	$scope.solicitationReviewer.solicitationId = $scope.solicitationId;
						$scope.solicitationReviewer.reviewerId = $scope.reviewerId;
		                SolicitationReviewer.save($scope.solicitationReviewer, onSaveSuccess, onSaveError);
		            }*/
					
					$scope.save();
		        }

		        function onSaveSuccess (result) {
		            $scope.$emit('bidopscoreApp:solicitationReviewerUpdate', result);
		            $uibModalInstance.close(result);
		            $scope.isSaving = false;
		        }

		        function onSaveError () {
		        	$scope.isSaving = false;
		        }

				
			} ]);