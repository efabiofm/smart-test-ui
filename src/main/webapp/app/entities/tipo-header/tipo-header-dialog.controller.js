(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoHeaderDialogController', TipoHeaderDialogController);

    TipoHeaderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoHeader', 'Header'];

    function TipoHeaderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoHeader, Header) {
        var vm = this;

        vm.tipoHeader = entity;
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
            if (vm.tipoHeader.id !== null) {
                TipoHeader.update(vm.tipoHeader, onSaveSuccess, onSaveError);
            } else {
                TipoHeader.save(vm.tipoHeader, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:tipoHeaderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
