import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

// Configurazione Vite unificata
export default defineConfig({
  plugins: [react()],

  // Alias per import come "@/components/Button"
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'), // permette import tipo "@/api/api"
    },
  },

  // Live reload + proxy API verso backend
  server: {
    host: true,      
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      }
    }
  }
})
