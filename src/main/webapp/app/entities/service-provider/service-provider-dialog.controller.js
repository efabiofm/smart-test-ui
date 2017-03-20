(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServiceProviderDialogController', ServiceProviderDialogController);

    ServiceProviderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceProvider', 'Header'];

    function ServiceProviderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiceProvider, Header) {
        var vm = this;

        vm.serviceProvider = entity;
        vm.clear = clear;
        vm.save = save;
        vm.headers = Header.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serviceProvider.id !== null) {
                ServiceProvider.update(vm.serviceProvider, onSaveSuccess, onSaveError);
            } else {
                ServiceProvider.save(vm.serviceProvider, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:serviceProviderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
