'use strict';

describe('Controller Tests', function() {

    describe('EjecucionPrueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEjecucionPrueba, MockPrueba;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEjecucionPrueba = jasmine.createSpy('MockEjecucionPrueba');
            MockPrueba = jasmine.createSpy('MockPrueba');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EjecucionPrueba': MockEjecucionPrueba,
                'Prueba': MockPrueba
            };
            createController = function() {
                $injector.get('$controller')("EjecucionPruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:ejecucionPruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
