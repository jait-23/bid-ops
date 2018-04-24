(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationsDialogController', PrimaryEvaluationsDialogController);

    PrimaryEvaluationsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrimaryEvaluations'];

    function PrimaryEvaluationsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrimaryEvaluations) {
        var vm = this;

        vm.primaryEvaluations = entity;
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
            if (vm.primaryEvaluations.id !== null) {
                PrimaryEvaluations.update(vm.primaryEvaluations, onSaveSuccess, onSaveError);
            } else {
                PrimaryEvaluations.save(vm.primaryEvaluations, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:primaryEvaluationsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
