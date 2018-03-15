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

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solicitations.id !== null) {
                Solicitations.update(vm.solicitations, onSaveSuccess, onSaveError);
            } else {
                Solicitations.save(vm.solicitations, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
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
    }
})();
