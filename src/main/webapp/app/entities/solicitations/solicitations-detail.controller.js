(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDetailController', SolicitationsDetailController);

    SolicitationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', '$uibModal', '$http', 'previousState', 'entity', 'Solicitations', 'User'];

    function SolicitationsDetailController($scope, $rootScope, $stateParams, $uibModal, $http, previousState, entity, Solicitations, User) {
        var vm = this;
        vm.editForm;

        vm.solicitations = entity;
        
        vm.previousState = previousState.name;
        vm.users = User.query();
        $scope.extraInfo = vm.solicitations.requiredDocuments;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        
        vm.datePickerOpenStatus.finalFilingDate = false;
        vm.datePickerOpenStatus.lastUpdated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationsUpdate', function(event, result) {
            vm.solicitations = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        var onSaveFinished = function (result) {
        	console.log("done on finsihed");
        };
        
        $scope.solicitations = vm.solicitations.requiredDocuments;
        
        $scope.save = function() {
			var updatedSolicitations = {};
			angular.copy(vm.solicitations, updatedSolicitations);
			updatedSolicitations.requiredDocuments = JSON.stringify(updatedSolicitations.requiredDocuments);
			
			if (vm.solicitations.id != null) {
				Solicitations.update(updatedSolicitations, onSaveFinished);
        	} else { 
        		Solicitations.save(vm.solicitations, onSaveFinished);
        	}
        };
        
        $scope.uploadFile = function () {
            var fd = new FormData();
            
            var file = document.getElementById("uploadPDF").files[0];
           if(file != null)
           {  
	            fd.append('file', file);
	           var  locationUrl = "http://localhost:8080/swagger-ui/" + file.name;
	          
	            var filetype = file.type;
	                            
	           var type = filetype.substring(filetype.indexOf("/") + 1);
	            
	            var uploadUrl = 'file/upload';
	                       
	            $http({
	                url: uploadUrl,
	                method : 'POST',
	                data:  fd,
	                withCredentials: true,
	                headers: {'Content-Type': undefined },
	                transformRequest: angular.identity,
	                mimeType:"multipart/form-data",
	                contentType: false,
	                cache: false,
	                processData:false
	             }).then(function s(response) {console.log("Post Success"); vm.solicitations.requiredDocuments = locationUrl}, function e(response){console.log("Post failure");});
           }
        };
		
		$scope.saveExtraInfo = function(key, value) {
			if (vm.solicitations.requiredDocuments == null) {
				vm.solicitations.requiredDocuments = {};
			}
			vm.solicitations.requiredDocuments[key] = value;
			$scope.save();
		};
		
		
		$scope.deleteExtraInfo = function(key, value) {
			vm.solicitations.requiredDocuments[key] = value;
			$scope.save();

			var updatedSolicitations = {};
			angular.copy(vm.solicitations, updatedSolicitations);
				updatedSolicitations.requiredDocuments.platform = "";

			angular.copy(updatedSolicitations, vm.solicitations);
			$scope.save();

		};
		
		$scope.deleteManuals = function (item) {
        	var updatedSolicitations = {};
        	
        	angular.copy(vm.solicitations, updatedSolicitations);   
        	var index = updatedSolicitations.requiredDocuments.Manuals.indexOf(item);
        	
        	updatedSolicitations.requiredDocuments.Manuals.splice(index, 1);
        	angular.copy(updatedSolicitations, vm.solicitations);
        	 $scope.save();
        }
	
		$scope.saveDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-save-dialog.html',
				controller : 'SolicitationsDialogDetailsSaveController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.cancelDetailsModal = function() {
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-cancel-dialog.html',
				controller : 'SolicitationsDetailsCancelDialogController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.sendReviewModal = function(solicitationId, userId) {
			$scope.solicitationId = solicitationId;
			$scope.userId = userId;
			console.log($scope.solicitationId);
			console.log($scope.userId);
			$uibModal.open({
				templateUrl : 'app/entities/solicitations/solicitations-details-review-dialog.html',
				controller : 'SolicitationsDialogDetailsReviewController',
				scope : $scope,
				size : 'lg'
			});
		}
		
		$scope.requiredDocumentsInfoCreateModal = function() {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsupload-dialog.html',
                 controller: 'SolicitationsUploadDialogController',
                 scope : $scope,
                 size: 'lg'
            });
        }
		
		$scope.requiredDocumentsInfoEditModal = function() {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsedit-dialog.html',
                 controller: 'SolicitationsEditDialogController',
                 scope : $scope,
                 size: 'lg'
        	});
		}
		
		$scope.requiredDocumentsInfoDeleteModal = function(item) {        	
			$uibModal.open({
                 templateUrl: 'app/entities/solicitations/solicitationsdelete-dialog.html',
                 controller: 'SolicitationsDeleteDialogController',
                 scope : $scope,
                 size: 'lg',
                 resolve: {
                     ManualItem: function () {
                    	  console.log(item);
                       return item;
                     }
                   }
            });
        }
        
        $scope.tabs = [{
            title: 'Details',
            url: 'details.tpl.html'
        }, {
            title: 'Upload Documents',
            url: 'Documents.tpl.html'
        }];

    $scope.currentTab = 'details.tpl.html';

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
    }
    
    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab;
    }

    
    }
})();
