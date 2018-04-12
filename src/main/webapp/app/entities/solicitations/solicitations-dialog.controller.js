(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDialogController', SolicitationsDialogController);

    SolicitationsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Solicitations'];

    function SolicitationsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Solicitations) {
        var vm = this;

        vm.solicitations = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.solicitations.status= "New";
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solicitations.id !== null) {
            	console.log(vm.solicitations);
                Solicitations.update(vm.solicitations, onSaveSuccess, onSaveError);
            } else {
            	console.log(vm.solicitations);
                Solicitations.save(vm.solicitations, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
        	console.log(result);
            $scope.$emit('bidopscoreApp:solicitationsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.finalFilingDate = false;
        vm.datePickerOpenStatus.lastUpdated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        /* $scope.uploadFile = function() {
			var fd = new FormData();

			var file = document.getElementById("uploadPDF").files[0];
			if (file != null) {
				fd.append('file', file);
				var locationUrl = "swagger-ui/"
									+ file.name;
				console.log("the saved location of the file is: "
													+ locationUrl);
				var filetype = file.type;

				var type = filetype
							.substring(filetype.indexOf("/") + 1);

				var uploadUrl = 'file/upload';

				$http({
					url : uploadUrl,
					method : 'POST',
					data : fd,
					withCredentials : true,
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity,
					mimeType : "multipart/form-data",
					contentType : false,
					cache : false,
					processData : false
				})
				.then(
					function s(response) {
						console.log("Post Success");
						vm.solicitations.requiredDocuments = locationUrl;
						if(vm.solicitations.siteExtraInfo != null)
						{
							vm.managedSite.siteExtraInfo = JSON.stringify(vm.managedSite.siteExtraInfo);
						}
						else{
						vm.managedSite["siteExtraInfo"] = null;	
						}
						ManagedSite.update({id: vm.managedSite.id}, vm.managedSite,onSaveFinished);
					}, function e(response) {
						console.log("Post failure");
				});
			}
		}; */
        
        $scope.uploadFile = function () {
            var fd = new FormData();
            
            var file = document.getElementById("uploadPDF").files[0];
           if(file != null)
           {  
	            fd.append('file', file);
	          var  locationUrl = "https://localhost:8080/swagger-ui/" + file.name;
	          
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
	             }).then(function s(response) {console.log("Post Success"); vm.solicitations.requiredDocuments = locationUrl; Solicitations.save(vm.solicitations, onSaveFinished);}, function e(response){console.log("Post failure");});
           }
        };
        
    }
})();
