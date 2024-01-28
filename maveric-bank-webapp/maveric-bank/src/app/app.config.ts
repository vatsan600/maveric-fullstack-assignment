import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { ActivatedRoute, provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpClientModule } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),importProvidersFrom(HttpClientModule)]
};
