(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDialogController', BiddersDialogController);

    BiddersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Bidders', 'Solicitations', 'Jhi_user'];

    function BiddersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Bidders, Solicitations, Jhi_user) {
        var vm = this;

        vm.bidders = entity;
        vm.clear = clear;
        vm.save = save;
        vm.solicitations = Solicitations.query();
        vm.jhi_users = Jhi_user.query({filter: 'bidders-is-null'});
        $q.all([vm.bidders.$promise, vm.jhi_users.$promise]).then(function() {
            if (!vm.bidders.jhi_userId) {
                return $q.reject();
            }
            return Jhi_user.get({id : vm.bidders.jhi_userId}).$promise;
        }).then(function(jhi_user) {
            vm.jhi_users.push(jhi_user);
        });
        vm.solicitations = Solicitations.query({filter: 'bidders-is-null'});
        $q.all([vm.bidders.$promise, vm.solicitations.$promise]).then(function() {
            if (!vm.bidders.solicitationsId) {
                return $q.reject();
            }
            return Solicitations.get({id : vm.bidders.solicitationsId}).$promise;
        }).then(function(solicitations) {
            vm.solicitations.push(solicitations);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bidders.id !== null) {
                Bidders.update(vm.bidders, onSaveSuccess, onSaveError);
            } else {
                Bidders.save(vm.bidders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:biddersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
