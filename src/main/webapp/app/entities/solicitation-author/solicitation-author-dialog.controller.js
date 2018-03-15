(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationAuthorDialogController', SolicitationAuthorDialogController);

    SolicitationAuthorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SolicitationAuthor'];

    function SolicitationAuthorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SolicitationAuthor) {
        var vm = this;

        vm.solicitationAuthor = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solicitationAuthor.id !== null) {
                SolicitationAuthor.update(vm.solicitationAuthor, onSaveSuccess, onSaveError);
            } else {
                SolicitationAuthor.save(vm.solicitationAuthor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:solicitationAuthorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
