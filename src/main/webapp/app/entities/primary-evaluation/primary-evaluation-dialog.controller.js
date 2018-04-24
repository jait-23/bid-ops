(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationDialogController', PrimaryEvaluationDialogController);

    PrimaryEvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrimaryEvaluation'];

    function PrimaryEvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrimaryEvaluation) {
        var vm = this;

        vm.primaryEvaluation = entity;
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
            if (vm.primaryEvaluation.id !== null) {
                PrimaryEvaluation.update(vm.primaryEvaluation, onSaveSuccess, onSaveError);
            } else {
                PrimaryEvaluation.save(vm.primaryEvaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:primaryEvaluationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
