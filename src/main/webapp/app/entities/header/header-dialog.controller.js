(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HeaderDialogController', HeaderDialogController);

    HeaderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Header', 'TipoHeader', 'ServiceProvider'];

    function HeaderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Header, TipoHeader, ServiceProvider) {
        var vm = this;

        vm.header = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipoheaders = TipoHeader.query();
        vm.serviceproviders = ServiceProvider.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.header.id !== null) {
                Header.update(vm.header, onSaveSuccess, onSaveError);
            } else {
                Header.save(vm.header, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:headerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
