import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

// Configurazione Vite per React
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'), // permette import tipo "@/api/api"
    },
  },
});
