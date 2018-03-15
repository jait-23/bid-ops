(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationReviewerDialogController', SolicitationReviewerDialogController);

    SolicitationReviewerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SolicitationReviewer'];

    function SolicitationReviewerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SolicitationReviewer) {
        var vm = this;

        vm.solicitationReviewer = entity;
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
            if (vm.solicitationReviewer.id !== null) {
                SolicitationReviewer.update(vm.solicitationReviewer, onSaveSuccess, onSaveError);
            } else {
                SolicitationReviewer.save(vm.solicitationReviewer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:solicitationReviewerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
