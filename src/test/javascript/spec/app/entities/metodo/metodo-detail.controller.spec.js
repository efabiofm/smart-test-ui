'use strict';

describe('Controller Tests', function() {

    describe('Metodo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMetodo, MockParametro, MockPrueba, MockServicio;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMetodo = jasmine.createSpy('MockMetodo');
            MockParametro = jasmine.createSpy('MockParametro');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockServicio = jasmine.createSpy('MockServicio');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Metodo': MockMetodo,
                'Parametro': MockParametro,
                'Prueba': MockPrueba,
                'Servicio': MockServicio
            };
            createController = function() {
                $injector.get('$controller')("MetodoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:metodoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
