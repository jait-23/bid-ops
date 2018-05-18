(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('FilesDialogController', FilesDialogController);

    FilesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Files', 'Solicitations'];

    function FilesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Files, Solicitations) {
        var vm = this;

        vm.files = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.solicitations = Solicitations.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.files.id !== null) {
                Files.update(vm.files, onSaveSuccess, onSaveError);
            } else {
                Files.save(vm.files, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:filesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setContent = function ($file, files) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        files.content = base64Data;
                        files.contentContentType = $file.type;
                    });
                });
            }
        };

    }
})();
